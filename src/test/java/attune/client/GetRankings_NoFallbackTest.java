package attune.client;

import static attune.client.WireMockUtils.code404;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

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

public class GetRankings_NoFallbackTest {
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8089).httpsPort(8443));

	private static final String URL_PATH_RANKING = "/entities/ranking";

	private String authToken;
	private AttuneConfigurable attuneConfig;
	private AttuneClient attuneClient;
	@Before
    public void before() throws Exception {
        this.authToken  = "some-auth-token";
        this.attuneConfig = new AttuneConfigurable("http://localhost:8089", 1000, 200, 123.0d, 10000.0d);
        this.attuneConfig.updateFallbackToDefaultMode(false);
        this.attuneConfig.setEnableCompression(false);
        attuneClient = AttuneClient.buildWith(attuneConfig);
        WireMock.resetAllRequests();
    }


	@Test
	public void getRankingsTest() throws ApiException {
		stubFor(post(urlEqualTo("/entities/ranking"))
			.willReturn(aResponse()
			    .withStatus(200)
			    .withHeader("Content-Type", "application/json")
			    .withBodyFile("GetRankings-positive.json")));
			    //.withBody("{\"ranking\":[\"some\",\"valid\",\"values\",\"from\",\"mock\",\"server\"],\"cell\":\"wiremocktest-postive\"}")));

		final String[] idList = {"1001", "1002", "1003", "1004"};
		RankingParams rankingParams = buildRankingParams(idList);
		ensureNoRankingCallsSoFar();
        RankedEntities rankings = attuneClient.getRankings(rankingParams, authToken);
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
        try {
			attuneClient.getRankings(rankingParams, authToken);
	        failBecauseExceptionWasNotThrown(ApiException.class);
        } catch(ApiException e) {
        	assertThat(e).has(code404);
        }
	}

	@Test
	public void getRankingsBadRequestTest() throws ApiException {
		stubFor(post(urlEqualTo(URL_PATH_RANKING))
			.willReturn(aResponse()
			    .withStatus(400)
			    .withHeader("Content-Type", "application/json")
			    .withBodyFile("GetRankings-positive.json")));

		final String[] idList = {"1001", "1002", "1003", "1004"};
		RankingParams rankingParams = buildRankingParams(idList);
		ensureNoRankingCallsSoFar();
        try {
			attuneClient.getRankings(rankingParams, authToken);
	        failBecauseExceptionWasNotThrown(ApiException.class);
        } catch(ApiException e) {
        	assertThat(e).has(code404);
        }
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
	private void sendNRequestsAndVerifyEdges(final String[] idList, final int times,
			int sleepBetweenRequestsMilliSeconds, final String[] firstValues, final String[] finalValues,
			boolean expectFailure) throws ApiException {
		RankingParams rankingParams = buildRankingParams(idList);
		RankedEntities rankings = null;
		for (int i = times; i > 0; i--) {
			try {
				rankings = attuneClient.getRankings(rankingParams, authToken);

				if (i == times) {
					assertThat(rankings.getRanking()).containsOnly(firstValues);
				}
				assertThat(rankings.getRanking()).containsOnly(finalValues);
			} catch (ApiException e) {
				if (expectFailure) {
					// eat exception
				} else {
					throw e;
				}
			}
			if (sleepBetweenRequestsMilliSeconds > 0) {
				Uninterruptibles.sleepUninterruptibly(sleepBetweenRequestsMilliSeconds, TimeUnit.MILLISECONDS);
			}
		}
	}

	/**
	 * Circuit should open and heal even if fallback is off.
	 * @throws ApiException
	 */
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

		sendNRequestsAndVerifyEdges(healCircuitIdList, 5, 100, validIds, validIds, false); // circuit is normal - normal response from server
		sendNRequestsAndVerifyEdges(breakCircuitIdList, 50, 100, breakCircuitIdList, breakCircuitIdList, true); // fallback resp due to failure and circuit eventually opens
		sendNRequestsAndVerifyEdges(healCircuitIdList, 50, 100, healCircuitIdList, validIds, true); //circuit is open, but heals
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
	}

	private void verifyRankingCall(String... idList) {
		WireMockUtils.verifyBodyParts(URL_PATH_RANKING, idList);
	}
}
