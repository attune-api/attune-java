package io.swagger.attune;

import io.swagger.attune.model.AnonymousResult;
import io.swagger.attune.model.Customer;
import io.swagger.attune.model.RankedEntities;
import io.swagger.attune.model.RankingParams;

/**
 * Created by sudnya on 6/2/15.
 */
public class MockClient implements RankingClient {
    public String getAuthToken(String clientId, String clientSecret) throws ApiException {
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
