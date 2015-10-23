package attune.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import attune.client.model.RankedEntities;
import attune.client.model.RankingParams;
import jersey.repackaged.com.google.common.collect.Lists;

public class WiremockTest {
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(8089);

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
        this.attuneConfig = new AttuneConfigurable("http://localhost:8089");
        attuneClient = AttuneClient.buildWith(attuneConfig);
    }

	/** == getRankings tests begin == **/
	@Test
	public void getRankingsTest() throws ApiException {
		stubFor(post(urlPathMatching("/entities/ranking.*"))
			.willReturn(aResponse()
		    .withStatus(200)
		    .withHeader("Content-Type", "application/json")
		    .withBodyFile("GetRankings-positive.json")));
		
		RankingParams rankingParams = buildRankingParams("1001", "1002", "1003", "1004");
        RankedEntities rankings = attuneClient.getRankings(rankingParams, authToken);
        assertThat(rankings).isNotNull();
        assertThat(rankings.getRanking()).containsOnly("some","valid","values","from","mock","server");
        assertThat(rankings.getCell()).isEqualTo("wiremocktest-postive");
	}

	@Test
	public void getRankingsFallbackTest() throws ApiException {
		stubFor(post(urlPathMatching("/entities/ranking.*"))
			.willReturn(aResponse()
		    .withStatus(500)));
		
		RankingParams rankingParams = buildRankingParams("testing", "fallback");

        RankedEntities rankings = attuneClient.getRankings(rankingParams, authToken);
        assertThat(rankings).isNotNull();
        assertThat(rankings.getRanking()).containsOnly("testing", "fallback");
        assertThat(rankings.getCell()).isNull();
	}


	@Test
	public void getRankingsBadResponseTest() throws ApiException {
		stubFor(post(urlPathMatching("/entities/ranking.*"))
			.willReturn(aResponse()
		    .withStatus(200)
		    .withHeader("Content-Type", "application/json")
		    .withBodyFile("invalid-json.txt")));
		
		RankingParams rankingParams = buildRankingParams("invalid", "json");
        RankedEntities rankings = attuneClient.getRankings(rankingParams, authToken);
        assertThat(rankings).isNotNull();
        assertThat(rankings.getRanking()).containsOnly("invalid", "json");
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
	/** == getRankings tests end == **/

}
