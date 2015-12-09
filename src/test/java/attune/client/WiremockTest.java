package attune.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.common.util.concurrent.Uninterruptibles;

import attune.client.model.RankedEntities;
import attune.client.model.RankingParams;
import jersey.repackaged.com.google.common.collect.Lists;

public class WiremockTest {
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8089).httpsPort(8443));
	private static final String URL_PATH_RANKING = "/entities/ranking";


    @Test
    public void bindTest() {
        stubFor(get(urlPathMatching("/anonymous/.*"))
            .withHeader("Accept", equalTo("application/json"))
            .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "text/xml")
            .withBody("<response>Some content</response>")));

    }

    private String authToken;
    private AttuneConfigurable attuneConfig;
    private AttuneClient attuneClient;

    @Before
    public void before() throws Exception {
        this.authToken  = "some-auth-token";
        this.attuneConfig = new AttuneConfigurable("http://localhost:8089", 1000, 200, 1.0d, 1.0d);
        this.attuneConfig.updateFallbackToDefaultMode(true);
        this.attuneConfig.setRetryCount(0);
        this.attuneConfig.setEnableCompression(false);
        attuneClient = AttuneClient.buildWith(attuneConfig);
        WireMock.resetAllRequests();
    }

	/** == getRankings tests begin == **/
	@Test
	public void getRankingsTest() throws ApiException {
		stubFor(post(urlEqualTo(URL_PATH_RANKING))
			.willReturn(aResponse()
			    .withStatus(200)
			    .withHeader("Content-Type", "application/json")
			    .withBodyFile("GetRankings-positive.json")));

		final String[] idList = {"1001", "1002", "1003", "1004"};
		RankingParams rankingParams = buildRankingParams(idList);
		ensureNoRankingCallsSoFar();
        RankedEntities rankings = attuneClient.getRankings(rankingParams, authToken);
        //verify(1, postRequestedFor(urlEqualTo(URL_PATH_RANKING)).withHeader(HttpHeaders.ACCEPT_ENCODING, equalTo("gzip,x-gzip")));
        verifyRankingCall(idList);
        assertThat(rankings).isNotNull();
        assertThat(rankings.getRanking()).containsOnly("some","valid","values","from","mock","server");
        assertThat(rankings.getCell()).isEqualTo("wiremocktest-postive");
    }


	@Test
	public void getRankingsNotFoundTest() throws ApiException {
		stubFor(post(urlEqualTo(URL_PATH_RANKING))
			.willReturn(aResponse()
			    .withStatus(404)
			    .withHeader("Content-Type", "application/json")
			    .withBodyFile("GetRankings-positive.json")));

		final String[] idList = {"1001", "1002", "1003", "1004"};
		RankingParams rankingParams = buildRankingParams(idList);
		ensureNoRankingCallsSoFar();
        RankedEntities rankings = attuneClient.getRankings(rankingParams, authToken);
        verifyRankingCall(idList);
        assertThat(rankings).isNotNull();
        assertThat(rankings.getRanking()).containsOnly(idList);
	}

	@Test
	public void getRankingsFallbackTest() throws ApiException {
		stubFor(post(urlEqualTo(URL_PATH_RANKING))
			.willReturn(aResponse()
					.withStatus(500)));
		final String[] idList = {"testing", "fallback"};
		RankingParams rankingParams = buildRankingParams(idList);

        ensureNoRankingCallsSoFar();
        RankedEntities rankings = attuneClient.getRankings(rankingParams, authToken);
        //make sure the request actually made it to the server
        verifyRankingCall(idList);
        assertThat(rankings).isNotNull();
        assertThat(rankings.getRanking()).containsOnly(idList); //response from fallback
        assertThat(rankings.getCell()).isNull();
    }

	@Test
	public void getBreakerNoOpenErrorTest() throws ApiException {
		stubFor(post(urlEqualTo(URL_PATH_RANKING))
			.withRequestBody(containing("force"))
			.willReturn(aResponse()
					.withStatus(200).withHeader("Content-Type", "application/json")
				    .withBodyFile("GetRankings-positive.json")));
		stubFor(post(urlEqualTo(URL_PATH_RANKING))
				.withRequestBody(containing("heal"))
				.willReturn(aResponse()
						.withStatus(200).withHeader("Content-Type", "application/json")
					    .withBodyFile("GetRankings-positive.json")));
		final String[] breakCircuitIdList = {"force", "circuit", "breaker", "open!"};
		final String[] healCircuitIdList = {"heal", "circuit", "breaker"};

		String[] validIds = {"some","valid","values","from","mock","server"};

		sendNRequestsAndVerifyEdges(healCircuitIdList, 5, 100, validIds, validIds); // all responses normal
		sendNRequestsAndVerifyEdges(breakCircuitIdList, 50, 100, validIds, validIds);
		sendNRequestsAndVerifyEdges(healCircuitIdList, 50, 100, validIds, validIds);
	}

	@Test
	public void getBreakerOpenTest() throws ApiException {
		stubFor(post(urlEqualTo(URL_PATH_RANKING))
			.withRequestBody(containing("force"))
			.willReturn(aResponse()
					.withStatus(500).withHeader("Content-Type", "application/json")
				    .withBodyFile("GetRankings-positive.json")));
		stubFor(post(urlEqualTo(URL_PATH_RANKING))
				.withRequestBody(containing("heal"))
				.willReturn(aResponse()
						.withStatus(200).withHeader("Content-Type", "application/json")
					    .withBodyFile("GetRankings-positive.json")));
		final String[] breakCircuitIdList = {"force", "circuit", "breaker", "open!"};
		final String[] healCircuitIdList = {"heal", "circuit", "breaker"};

		String[] validIds = {"some","valid","values","from","mock","server"};

		sendNRequestsAndVerifyEdges(healCircuitIdList, 5, 100, validIds, validIds); // circuit is normal - normal response from server
		sendNRequestsAndVerifyEdges(breakCircuitIdList, 50, 100, breakCircuitIdList, breakCircuitIdList); // fallback resp due to failure and circuit eventually opens
		sendNRequestsAndVerifyEdges(healCircuitIdList, 50, 100, healCircuitIdList, validIds); //circuit is open, but heals
	}

	/**
	 * Send n requests and verify the first and last responses.
	 * @param idList
	 * @param times
	 * @param sleepBetweenRequestsMilliSeconds
	 * @param firstValues
	 * @param finalValues
	 * @throws ApiException
	 */
	private void sendNRequestsAndVerifyEdges(final String[] idList, final int times, int sleepBetweenRequestsMilliSeconds, final String[] firstValues, final String[] finalValues) throws ApiException {
		RankingParams rankingParams = buildRankingParams(idList);
		RankedEntities rankings = null;
		for (int i = times; i > 0; i --) {
			rankings = attuneClient.getRankings(rankingParams, authToken);
			if (i == times) {
				assertThat(rankings.getRanking()).containsOnly(firstValues);
			}
			if (sleepBetweenRequestsMilliSeconds > 0) {
				Uninterruptibles.sleepUninterruptibly(sleepBetweenRequestsMilliSeconds, TimeUnit.MILLISECONDS);
			}
		}
		assertThat(rankings.getRanking()).containsOnly(finalValues);
	}

    @Test
    public void getRankingsBadResponseTest() throws ApiException {
        stubFor(post(urlEqualTo("/entities/ranking"))
            .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBodyFile("invalid-json.txt")));

        final String[] idList = {"invalid", "json"};
        RankingParams rankingParams = buildRankingParams(idList);
        ensureNoRankingCallsSoFar();
        RankedEntities rankings = attuneClient.getRankings(rankingParams, authToken);
        verifyRankingCall(idList);
        assertThat(rankings).isNotNull();
        assertThat(rankings.getRanking()).containsOnly(idList); //response from fallback
        assertThat(rankings.getCell()).isNull();
    }

    private RankingParams buildRankingParams(String... idList) {
        RankingParams rankingParams = new RankingParams();
        rankingParams.setAnonymous("anon-id");
        rankingParams.setView("b/mens-pants");
        rankingParams.setEntityType("products");
        rankingParams.setIds(Lists.newArrayList(idList));
        return rankingParams;
	}
	
	private void ensureNoRankingCallsSoFar() {
		WireMockUtils.ensureNoPostSoFarTo(URL_PATH_RANKING);
		WireMockUtils.ensureNoGetSoFarTo(URL_PATH_RANKING);
	}

	private void verifyRankingCall(String... idList) {
		WireMockUtils.verifyBodyParts(URL_PATH_RANKING, idList);
	}

	@Test
	public void defaultRetryCountTest() throws ApiException {
		stubFor(post(urlEqualTo(URL_PATH_RANKING))
				.withRequestBody(containing("force"))
				.willReturn(aResponse()
						.withStatus(500).withHeader("Content-Type", "application/json")
					    .withBodyFile("GetRankings-positive.json")));
		final String[] ids = {"some", "values"};
		attuneClient.getRankings(buildRankingParams(ids), authToken);
		verify(1, postRequestedFor(urlEqualTo(URL_PATH_RANKING)));
	}

	@Test
	public void retryCountTest() throws ApiException {
		setCustomRetryCount();
		stubFor(post(urlEqualTo(URL_PATH_RANKING))
				.withRequestBody(containing("gimme-500"))
				.willReturn(aResponse()
						.withStatus(500).withHeader("Content-Type", "application/json")
					    .withBodyFile("GetRankings-positive.json")));
		stubFor(post(urlEqualTo(URL_PATH_RANKING))
				.withRequestBody(containing("gimme-400"))
				.willReturn(aResponse()
						.withStatus(400).withHeader("Content-Type", "application/json")
					    .withBodyFile("GetRankings-positive.json")));
		// 5xx should result in retry
		final String[] ids500 = {"gimme-500", "values"};
		attuneClient.getRankings(buildRankingParams(ids500), authToken);
		verify(3, postRequestedFor(urlEqualTo(URL_PATH_RANKING)));
		WireMock.resetAllRequests();

		// no retries on 4xx codes
		final String[] ids400 = {"gimme-400", "values"};
		attuneClient.getRankings(buildRankingParams(ids400), authToken);
		verify(1, postRequestedFor(urlEqualTo(URL_PATH_RANKING)));
		WireMock.resetAllRequests();

		// test 500 once more for good measure
		attuneClient.getRankings(buildRankingParams(ids500), authToken);
		verify(3, postRequestedFor(urlEqualTo(URL_PATH_RANKING)));
	}

	private void setCustomRetryCount() {
		this.authToken  = "some-auth-token";
        this.attuneConfig = new AttuneConfigurable("http://localhost:8089", 1000, 200, 123.0d, 10000.0d);
        this.attuneConfig.updateFallbackToDefaultMode(true);
        this.attuneConfig.setRetryCount(2);
        this.attuneConfig.setEnableCompression(false);
        attuneClient = AttuneClient.buildWith(attuneConfig);
        WireMock.resetAllRequests();
	}
	/** == getRankings tests end == **/


}
