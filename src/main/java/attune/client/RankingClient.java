package attune.client;

import attune.client.model.AnonymousResult;
import attune.client.model.Customer;
import attune.client.model.RankedEntities;
import attune.client.model.RankingParams;

/**
 * Created by sudnya on 6/2/15.
 */
public interface RankingClient {
    public AnonymousResult createAnonymous(String authToken) throws ApiException;
    public void bind(String anonymousId, String customerId, String authToken) throws ApiException;
    public Customer getBoundCustomer(String anonymousId, String authToken) throws ApiException;
    public RankedEntities getRankings(RankingParams rankingParams, String authToken) throws ApiException;
}
