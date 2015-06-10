package attune.client;

import attune.client.api.Anonymous;
import attune.client.api.Entities;
import attune.client.model.AnonymousResult;
import attune.client.model.Customer;
import attune.client.model.RankedEntities;
import attune.client.model.RankingParams;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;


/**
 * Created by sudnya on 5/27/15.
 */
public class AttuneClient implements RankingClient  {
    private AttuneConfigurable attuneConfigurable;
    private Entities entities;
    private Anonymous anonymous;

    private static AttuneClient instance;

    public static AttuneClient getInstance(){
        if (instance == null) {
            //double checked locking for thread safe singleton
            synchronized (AttuneClient.class) {
                if(instance == null){
                    instance = new AttuneClient();
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

    private AttuneClient() {
        attuneConfigurable = new AttuneDefault();
        entities           = new Entities();
        anonymous          = new Anonymous();
    }

    /**
     * Requests an auth token
     * @author sudnya
     * @example
     * String token = attuneClient.getAuthToken(clientId, clientSecret)
     * @return An auth token
     */
    public String getAuthToken() throws ApiException {
        int counter = 0;
        String clientId     = attuneConfigurable.getClientId();
        String clientSecret = attuneConfigurable.getClientSecret();

        if (clientId == null)
            throw new IllegalArgumentException("clientId is required");
        if (clientSecret == null)
            throw new IllegalArgumentException("clientSecret is required");

        ClientConfig clientConfig = new DefaultClientConfig();
        Client client             = Client.create(clientConfig);
        WebResource authResource  = client.resource(attuneConfigurable.endpoint + "/oauth/token");
        MultivaluedMap formData   = new MultivaluedMapImpl();

        formData.add("client_id"    , clientId);
        formData.add("client_secret", clientSecret);
        formData.add("grant_type"   , "client_credentials");

        ClientResponse response = authResource.type(MediaType. APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, formData);
        String body             = response.getEntity(String.class);
        String accessToken      = null;

        while (true) {
            try {
                JSONObject json = new JSONObject(body);
                accessToken = json.getString("access_token");
                break;
            } catch (JSONException e) {
                ++counter;
                if (counter >= attuneConfigurable.getRetries()) {
                    throw new ApiException();
                }
            }
        }
        return accessToken;
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
                if (counter >= attuneConfigurable.getRetries()) {
                    throw new ApiException();
                }
            }
        }
        return retVal;
    }

    /**
     */
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
                if (counter >= attuneConfigurable.getRetries()) {
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
                if (counter >= attuneConfigurable.getRetries()) {
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
                ++counter;
                if (counter >= attuneConfigurable.getRetries()) {
                    throw new ApiException();
                }
            }
        }
        return retVal;
    }
}
