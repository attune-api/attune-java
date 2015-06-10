package attune.client;

import attune.client.model.AnonymousResult;
import attune.client.model.Customer;
import attune.client.model.RankedEntities;
import attune.client.model.RankingParams;

/**
 * Created by sudnya on 6/2/15.
 */
public class MockClient implements RankingClient {
    public String getAuthToken() throws ApiException {
        return "";
    }


    public AnonymousResult createAnonymous(String authToken) throws ApiException {
        return new AnonymousResult();
    }


    public void bind(String anonymousId, String customerId, String authToken) throws ApiException {
    }


    public Customer getBoundCustomer(String anonymousId, String authToken) throws ApiException {
        return new Customer();
    }


    public RankedEntities getRankings(RankingParams rankingParams, String authToken) throws ApiException {
        RankedEntities rankedEntities = new RankedEntities();
        rankedEntities.setRanking(rankingParams.getIds());
        return rankedEntities;
    }

}
