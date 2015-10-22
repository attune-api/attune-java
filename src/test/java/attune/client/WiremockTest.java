package attune.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import attune.client.model.RankedEntities;
import attune.client.model.RankingParams;

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
	@Before
    public void before() throws Exception {
        //this.authToken  = "4d5c2671-cee6-4f1f-b3bb-13648728b62d";
        //this.attuneConfig = new AttuneConfigurable("http://localhost:8765", 5.0, 10.0);
        this.authToken  = "388dee30-394d-4a85-9e79-d951e5c3e292";
        this.attuneConfig = new AttuneConfigurable("http://localhost:8089");
    }

	@Test
	public void getRankingsTest() throws ApiException {
		stubFor(post(urlPathMatching("/entities/ranking.*"))
				//.withHeader("Accept", equalTo("application/json"))
				.willReturn(aResponse()
		                .withStatus(200)
		                .withHeader("Content-Type", "application/json")
		                .withBodyFile("GetRankings-positive.json")));
		
		AttuneClient client = AttuneClient.getInstance(attuneConfig);
		RankingParams rankingParams = new RankingParams();
        rankingParams.setAnonymous("anon-id");
        rankingParams.setView("b/mens-pants");
        rankingParams.setEntityType("products");

        List<String> idList = new ArrayList<String>();
        idList.add("1001");
        idList.add("1002");
        idList.add("1003");
        idList.add("1004");

        rankingParams.setIds(idList);

        RankedEntities rankings = client.getRankings(rankingParams, authToken);
        assertThat(rankings).isNotNull();
        assertThat(rankings.getRanking()).containsOnly("some","valid","values","from","mock","server");
        assertThat(rankings.getCell()).isEqualTo("wiremocktest-postive");
	}

	@Test
	public void getRankingsFallbackTest() throws ApiException {
		stubFor(post(urlPathMatching("/entities/ranking.*"))
				//.withHeader("Accept", equalTo("application/json"))
				.willReturn(aResponse()
		                .withStatus(500)));
		
		AttuneClient client = AttuneClient.getInstance(attuneConfig);
		RankingParams rankingParams = new RankingParams();
        rankingParams.setAnonymous("anon-id");
        rankingParams.setView("b/mens-pants");
        rankingParams.setEntityType("products");

        List<String> idList = new ArrayList<String>();
        idList.add("testing");
        idList.add("fallback");

        rankingParams.setIds(idList);

        RankedEntities rankings = client.getRankings(rankingParams, authToken);
        assertThat(rankings).isNotNull();
        assertThat(rankings.getRanking()).containsOnly("testing", "fallback");
        assertThat(rankings.getCell()).isNull();
	}


	@Test
	public void getRankingsBadResponseTest() throws ApiException {
		stubFor(post(urlPathMatching("/entities/ranking.*"))
				//.withHeader("Accept", equalTo("application/json"))
				.willReturn(aResponse()
		                .withStatus(200)
		                .withHeader("Content-Type", "application/json")
		                .withBodyFile("invalid-json.txt")));
		
		AttuneClient client = AttuneClient.getInstance(attuneConfig);
		RankingParams rankingParams = new RankingParams();
        rankingParams.setAnonymous("anon-id");
        rankingParams.setView("b/mens-pants");
        rankingParams.setEntityType("products");

        List<String> idList = new ArrayList<String>();
        idList.add("invalid");
        idList.add("json");

        rankingParams.setIds(idList);

        RankedEntities rankings = client.getRankings(rankingParams, authToken);
        assertThat(rankings).isNotNull();
        assertThat(rankings.getRanking()).containsOnly("invalid", "json");
        assertThat(rankings.getCell()).isNull();
	}
}
