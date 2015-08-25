package attune.client;

import attune.client.api.Anonymous;
import attune.client.api.Entities;
import attune.client.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sudnya on 5/27/15.
 */
public class AttuneClient implements RankingClient  {
    private final int MAX_RETRIES = 1;

    private AttuneConfigurable attuneConfigurable;
    private Entities entities;
    private Anonymous anonymous;
    private static AttuneClient instance;

    public static AttuneClient getInstance(AttuneConfigurable configurable) {
        if (instance == null) {
            //double checked locking for thread safe singleton
            synchronized (AttuneClient.class) {
                if (instance == null) {
                    instance = new AttuneClient(configurable);
                }
            }
        }
        return instance;
    }

    /**
     * Initializes a new AttuneClient
     * @author sudnya
     * @return A new client object with configuration parameters loaded from the config.properties file
     */
    private AttuneClient(AttuneConfigurable configurable) {
        attuneConfigurable = configurable;

        entities           = new Entities(attuneConfigurable);
        anonymous          = new Anonymous(attuneConfigurable);
    }

    /**
     * Overrides the default value of the fallBackToDefault mode
     * @author sudnya
     * @example
     * updateFallBackToDefault(true)
     * @param defaultFallBack
     */
    public void updateFallBackToDefault(boolean defaultFallBack) {
        attuneConfigurable.updateFallbackToDefaultMode(defaultFallBack);
    }

    /**
     * Overrides the default value of the test mode
     * @author sudnya
     * @example
     * updateTestMode(true)
     * @param testMode
     */
    public void updateTestMode(boolean testMode) {
        attuneConfigurable.updateTestMode(testMode);
    }

    /**
     * Overrides the default read timeout value (in seconds)
     * @author sudnya
     * @example
     * updateReadTimeout(0.25)
     * @param readTimeout (in seconds)
     */
    public void updateReadTimeout(Double readTimeout) {
        attuneConfigurable.updateReadTimeout(readTimeout);
    }

    /**
     * Overrides the connection timeout value (in seconds)
     * @author sudnya
     * @example
     * updateConnectionTimeout(0.50)
     * @param connectionTimeout (in seconds)
     */
    public void updateConnectionTimeout(Double connectionTimeout) {
        attuneConfigurable.updateConnectionTimeout(connectionTimeout);
    }

    /**
     * Requests an anonymous id, given an auth token
     * @author sudnya
     * @example
     * String token = attuneClient.createAnonymous(authToken)
     * @param authToken authentication token
     * @return An AnonymousResult object, do a getId on this object to get anonymousId
     */
    public AnonymousResult createAnonymous(String authToken) throws ApiException {
        int counter = 0;
        AnonymousResult retVal;

        while (true) {
            try {
                retVal = anonymous.create(authToken);
                break;
            } catch (ApiException ex) {
                ++counter;
                if (counter > MAX_RETRIES) {
                    throw new ApiException();
                }
            }
        }
        return retVal;
    }

    /**
     * Requests an anonymous id, given an auth token
     * @author sudnya
     * @example
     * String token = attuneClient.createAnonymous(authToken)
     * Binds one actor to another.
     * Binds one actor to another, allowing activities of those actors to be shared between the two.
     * @param anonymousId anonymousId
     * @param customerId customerId
     * @param authToken authentication token
     * @return BlacklistUpdateResponse
     */
    public void bind(String anonymousId, String customerId, String authToken) throws ApiException {
        int counter = 0;
        Customer customer = new Customer();
        customer.setCustomer(customerId);
        while (true) {
            try {
                anonymous.update(anonymousId, customer, authToken);
                break;
            } catch (ApiException ex) {
                ++counter;
                if (counter > MAX_RETRIES) {
                    throw new ApiException();
                }
            }
        }
    }


    /**
     * Binds an anonymous id to a customer, given an auth token
     * @author sudnya
     * @example
     * String token = attuneClient.getBinding(anonymousId, authToken)
     * @param anonymousId anonymousId
     * @param authToken authentication token
     * @return An AnonymousResult object, do a getId on this object to get anonymousId
     */
    public Customer getBoundCustomer(String anonymousId, String authToken) throws ApiException {
        int counter = 0;
        Customer retVal;

        while (true) {
            try {
                retVal = anonymous.get(anonymousId, authToken);
                break;
            } catch (ApiException ex) {
                ++counter;
                if (counter > MAX_RETRIES) {
                    throw new ApiException();
                }
            }
        }
        return retVal;
    }

    /**
     * Returns a ranking of the specified entities for the current user, given an auth token
     * @author sudnya
     * @example
     * RankedEntities rankings = client.getRankings(rankingParams, authToken)
     * @param rankingParams an object with the ranking parameters
     * @param authToken authentication token
     */
    public RankedEntities getRankings(RankingParams rankingParams, String authToken) throws ApiException {
        int counter = 0;
        RankedEntities retVal;

        while (true) {
            try {
                retVal = entities.getRankings(rankingParams, authToken);
                break;
            } catch (ApiException ex) {
                System.out.println(ex);
                ++counter;
                if (counter > MAX_RETRIES) {
                    if (attuneConfigurable.isFallBackToDefault()) {
                        return returnDefaultRankings(rankingParams);
                    }
                    throw new ApiException();
                }
            }
        }
        return retVal;
    }


    /**
     * Returns a batch of rankings of the given list of specified entities for the current user, given an auth token
     * @author sudnya
     * @example
     * List<RankedEntities> listOfRankings = client.batchGetRankings(rankingParamsList, authToken)
     * @param rankingParamsList list of objects with the ranking parameters
     * @param authToken authentication token
     */
    public List<RankedEntities> batchGetRankings(List<RankingParams> rankingParamsList, String authToken) throws ApiException {
        int counter = 0;
        List<RankedEntities> retVal;
        BatchRankingRequest batchRequest = new BatchRankingRequest();
        batchRequest.setRequests(rankingParamsList);
        BatchRankingResult result;

        while (true) {
            try {
                result = entities.batchGetRankings(batchRequest, authToken);
                break;
            } catch (ApiException ex) {
                System.out.println(ex);
                ++counter;
                if (counter > MAX_RETRIES) {
                    if (attuneConfigurable.isFallBackToDefault()) {
                        return returnBatchDefaultRankings(rankingParamsList);
                    }
                    throw new ApiException();
                }
            }
        }
        retVal = result.getResults();
        return retVal;
    }


    private RankedEntities returnDefaultRankings(RankingParams rankingParams) throws ApiException {
        RankedEntities rankedEntities = new RankedEntities();
        rankedEntities.setRanking(rankingParams.getIds());
        return rankedEntities;
    }

    private List<RankedEntities> returnBatchDefaultRankings(List<RankingParams> rankingParamsList) throws ApiException {
        List<RankedEntities> rankedEntityList = new ArrayList<>();
        for (RankingParams entry : rankingParamsList) {
            rankedEntityList.add(returnDefaultRankings(entry));
        }
        return rankedEntityList;
    }

    //TODO: this is for junit test purpose only, hence don't generate javadoc
    protected AttuneConfigurable getAttuneConfigurable() {
        return this.attuneConfigurable;
    }
}
