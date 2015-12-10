package attune.client;

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

import java.util.ArrayList;
import java.util.List;

import attune.client.model.RankingParams;

public abstract class BaseRankingTest {

	protected static final String URL_PATH_RANKING = "/entities/ranking";

	protected void ensureNoRankingCallsSoFar() {
		WireMockUtils.ensureNoPostSoFarTo(URL_PATH_RANKING);
		WireMockUtils.ensureNoGetSoFarTo(URL_PATH_RANKING);
	}

	protected void verifyRankingCall(String... idList) {
		WireMockUtils.verifyBodyParts(URL_PATH_RANKING, idList);
	}

	protected void verifyRankingGetCalled() {
		verifyRankingGetCalled(1);
	}

	protected void verifyRankingGetCalled(int i) {
		verify(i, getRequestedFor(urlPathEqualTo(URL_PATH_RANKING)));
        verify(0, postRequestedFor(urlPathEqualTo(URL_PATH_RANKING)));
	}

	protected RankingParams buildScopeRankingParams() {
		RankingParams retVal = new RankingParams();
		String cid = "customerId";
		String aid = "anonId";
		
		retVal.setAnonymous(aid);
		retVal.setCustomer(cid);
		retVal.setView("sales/view");
		retVal.setIds(new ArrayList<String>());
		retVal.setApplication("mobile_event_page");
		retVal.setEntityType("products");
		retVal.setEntitySource("scope");
		
		List<String> scopes = new ArrayList<String>();
	  scopes.add("sale=view");
	  retVal.setScope(scopes);
		return retVal;
	}
}
