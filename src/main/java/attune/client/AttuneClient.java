package attune.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import attune.client.api.Anonymous;
import attune.client.api.Entities;
import attune.client.model.AnonymousResult;
import attune.client.model.Customer;
import attune.client.model.RankedEntities;
import attune.client.model.RankingParams;

/**
 * Created by sudnya on 5/27/15.
 */
public class AttuneClient implements RankingClient  {
    private final int MAX_RETRIES = 1;

    private AttuneConfigurable attuneConfigurable;
    private Entities entities;
    private Anonymous anonymous;


    private AttuneClient(AttuneConfigurable configurable) {
        attuneConfigurable = configurable;

        entities           = new Entities(attuneConfigurable);
        anonymous          = new Anonymous(attuneConfigurable);
    }

    /**
     * Returns an instance of the client configured based on the provided {@link AttuneConfigurable} instance.
     * @param config configuration
     * @return instance of the client
     */
    public static AttuneClient buildWith(AttuneConfigurable config) {
        return new AttuneClient(config);
    }

    /**
     * Overrides the default value of the fallBackToDefault mode
     * @author sudnya
     * @param defaultFallBack
     */
    public void updateFallBackToDefault(boolean defaultFallBack) {
        attuneConfigurable.updateFallbackToDefaultMode(defaultFallBack);
    }

    /**
     * Overrides the default value of the test mode
     * @author sudnya
     * @param testMode
     */
    public void updateTestMode(boolean testMode) {
        attuneConfigurable.updateTestMode(testMode);
    }

    public String getAuthToken(String clientId, String clientSecret) throws ApiException {
        int counter = 0;

        if (clientId == null)
            throw new IllegalArgumentException("clientId is required");
        if (clientSecret == null)
            throw new IllegalArgumentException("clientSecret is required");

        Client client                             = ClientBuilder.newClient();
        WebTarget authResource                    = client.target(attuneConfigurable.getEndpoint()).path("oauth/token");
        MultivaluedMap<String, String> formData   = new MultivaluedHashMap<String, String>();

        formData.add("client_id"    , clientId);
        formData.add("client_secret", clientSecret);
        formData.add("grant_type"   , "client_credentials");

        String accessToken = null;
        Response response  = authResource.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.form(formData));
        String body        = response.readEntity(String.class);

        while (true) {
            try {
                JSONObject json = new JSONObject(body);
                accessToken = json.getString("access_token");
                break;
            } catch (JSONException e) {
                ++counter;
                if (counter > MAX_RETRIES) {
                    throw new ApiException();
                }
            }
        }
        return accessToken;
    }


    /**
     * Requests an anonymous id, given an auth token
     * @author sudnya
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
                    throw ex;
                }
            }
        }
        return retVal;
    }

    /**
     * Binds one actor to another, allowing activities of those actors to be shared between the two.
     * @author sudnya
     * Binds one actor to another, allowing activities of those actors to be shared between the two.
     * @param anonymousId anonymousId
     * @param customerId customerId
     * @param authToken authentication token
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
                    throw ex;
                }
            }
        }
    }


    /**
     * Returns the customer bound to a given anonymousId, for a given auth token
     * @author sudnya
     * @param anonymousId anonymousId
     * @param authToken authentication token
     * @return A customer that was associated to this anonymousId with a bind call
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
                    throw ex;
                }
            }
        }
        return retVal;
    }

    /**
     * Returns a ranking of the specified entities, given an auth token
     * @author sudnya
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
                ++counter;
                if (counter > MAX_RETRIES) {
                    if (attuneConfigurable.isFallBackToDefault()) {
                        return returnDefaultRankings(rankingParams, ex.getCode());
                    }
                    throw ex;
                }
            }
        }
        return retVal;
    }


    /**
     * Returns a list of rankings of the given list of specified entities, given an auth token
     * @author sudnya
     * @param rankingParamsList list of objects with the ranking parameters
     * @param authToken authentication token
     *
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
                ++counter;
                if (counter > MAX_RETRIES) {
                    if (attuneConfigurable.isFallBackToDefault()) {
                        return returnBatchDefaultRankings(rankingParamsList);
                    }
                    throw ex;
                }
            }
        }
        retVal = result.getResults();
        return retVal;
    }*/


    private RankedEntities returnDefaultRankings(RankingParams rankingParams, int errorCode) {
        RankedEntities rankedEntities = new RankedEntities();
        rankedEntities.setRanking(rankingParams.getIds());
        rankedEntities.setStatus(errorCode);
        return rankedEntities;
    }
/**
    private List<RankedEntities> returnBatchDefaultRankings(List<RankingParams> rankingParamsList) {
        List<RankedEntities> rankedEntityList = new ArrayList<>();
        for (RankingParams entry : rankingParamsList) {
            rankedEntityList.add(returnDefaultRankings(entry));
        }
        return rankedEntityList;
    }
*/
    //TODO: this is for junit test purpose only, hence don't generate javadoc
    protected AttuneConfigurable getAttuneConfigurable() {
        return this.attuneConfigurable;
    }
}
