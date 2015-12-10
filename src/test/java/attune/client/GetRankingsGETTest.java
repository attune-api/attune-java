package attune.client;

import static attune.client.WireMockUtils.code404;
import static attune.client.WireMockUtils.codeGatewayTimeout;
import static attune.client.WireMockUtils.codeInternalServerError;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import attune.client.model.RankedEntities;
import attune.client.model.RankingParams;

public class GetRankingsGETTest extends BaseRankingTest {
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8089).httpsPort(8443));

	private String authToken;
	private AttuneConfigurable attuneConfig;
	private AttuneClient attuneClient;
	@Before
    public void before() throws Exception {
        this.authToken  = "some-auth-token";
        this.attuneConfig = new AttuneConfigurable("http://localhost:8089", 1000, 200, 5.0d, 5.0d);
        this.attuneConfig.updateFallbackToDefaultMode(true);
        this.attuneConfig.setEnableCompression(false);
        attuneClient = AttuneClient.buildWith(attuneConfig);
        attuneClient.updateFallBackToDefault(true);
        WireMock.resetAllRequests();
    }


	@Test
	public void getRankingsTest() throws ApiException {
		stubFor(get(urlPathEqualTo(URL_PATH_RANKING))
			.willReturn(aResponse()
			    .withStatus(200)
			    .withHeader("Content-Type", "application/json")
			    .withBodyFile("GetRankings-positive.json")));

		RankingParams rankingParams = buildScopeRankingParams();
		ensureNoRankingCallsSoFar();
        RankedEntities rankings = attuneClient.getRankings(rankingParams, authToken);

        verifyRankingGetCalled();
        assertThat(rankings).isNotNull();
        assertThat(rankings.getRanking()).containsOnly("some","valid","values","from","mock","server");
        assertThat(rankings.getCell()).isEqualTo("wiremocktest-postive");
	}

	private final int DEFAULT_TRIES = 2;
	@Test
	public void rankingGetShouldNeverFallback() throws ApiException {
		stubFor(get(urlPathEqualTo(URL_PATH_RANKING))
			.willReturn(aResponse()
			    .withStatus(500)
			    .withHeader("Content-Type", "application/json")
			    .withBodyFile("GetRankings-positive.json")));

		RankingParams rankingParams = buildScopeRankingParams();
		ensureNoRankingCallsSoFar();
		try {
	        attuneClient.getRankings(rankingParams, authToken);
	        failBecauseExceptionWasNotThrown(ApiException.class);
		} catch(ApiException e) {
			verifyRankingGetCalled(DEFAULT_TRIES); // default tries is 2 (1 retry)
			assertThat(e).has(codeInternalServerError);
		}
	}

	private final int ONCE_SINCE_NO_RETRY_ON_4XX = 1;
	@Test
	public void rankingGetShouldNeverFallback404() throws ApiException {
		stubFor(get(urlPathEqualTo(URL_PATH_RANKING))
			.willReturn(aResponse()
			    .withStatus(404)
			    .withHeader("Content-Type", "application/json")
			    .withBodyFile("GetRankings-positive.json")));

		RankingParams rankingParams = buildScopeRankingParams();
		ensureNoRankingCallsSoFar();
		try {
	        attuneClient.getRankings(rankingParams, authToken);
	        failBecauseExceptionWasNotThrown(ApiException.class);
		} catch(ApiException e) {
			verifyRankingGetCalled(ONCE_SINCE_NO_RETRY_ON_4XX);
			assertThat(e).has(code404);
		}
	}


	@Test
	public void timeoutTest() {
        WireMock.resetAllRequests();
        
        stubFor(get(urlPathEqualTo(URL_PATH_RANKING))
    			.willReturn(aResponse()
    			    .withStatus(200).withFixedDelay(6000) //force timeout
    			    .withHeader("Content-Type", "application/json")
    			    .withBodyFile("GetRankings-positive.json")));
        
        RankingParams rankingParams = buildScopeRankingParams();
		ensureNoRankingCallsSoFar();
		try {
	        attuneClient.getRankings(rankingParams, authToken);
	        failBecauseExceptionWasNotThrown(ApiException.class);
		} catch(ApiException e) {
			//verifyRankingGetCalled(1); // only one call since hystrix timeout is same as read timeout!
			assertThat(e).has(codeGatewayTimeout);
		}
	}
}
