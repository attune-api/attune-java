package io.swagger.attune;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import io.swagger.attune.api.Anonymous;
import io.swagger.attune.api.Entities;
import io.swagger.attune.model.AnonymousResult;
import io.swagger.attune.model.Customer;
import io.swagger.attune.model.RankedEntities;
import io.swagger.attune.model.RankingParams;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Created by sudnya on 5/27/15.
 */
public class AttuneClient {
    private AttuneConfigurable attuneConfigurable;
    private Entities entities;
    private Anonymous anonymous;

    /**
     * Initializes a new AttuneClient
     * @author sudnya
     * Client attuneClient = new AttuneClient(endPoint, timeout);
     * @param Options for connection (see AttuneConfigurable)
     * @return A new client object
     */

    public AttuneClient() {
        attuneConfigurable = new AttuneDefault();
        entities  = new Entities();
        anonymous = new Anonymous();
    }

    /**
     * Requests an auth token
     * @author sudnya
     * @example
     * String token = attuneClient.getAuthToken(clientId, clientSecret)
     * @param clientId and clientSecret
     * @return An auth token
     */
    public String getAuthToken(String clientId, String clientSecret) throws ApiException {
        if (clientId == null)
            throw new IllegalArgumentException("clientId is required");
        if (clientSecret == null)
            throw new IllegalArgumentException("clientSecret is required");

        Client client            = Client.create(new DefaultClientConfig());
        WebResource authResource = client.resource(attuneConfigurable.endpoint + "/oauth/token");
        MultivaluedMap formData  = new MultivaluedMapImpl();

        formData.add("client_id"    , clientId);
        formData.add("client_secret", clientSecret);
        formData.add("grant_type"   , "client_credentials");

        ClientResponse response = authResource.type(MediaType. APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, formData);
        String body             = response.getEntity(String.class);
        String accessToken     = null;

        try {
            JSONObject json = new JSONObject(body);
            accessToken = json.getString("access_token");
        } catch (JSONException e) {
            throw new ApiException();
        } finally {
            return accessToken;
        }
    }


    /**
     * Requests an anonymous id, given an auth token
     * @author sudnya
     * @example
     * String token = attuneClient.createAnonymous(authToken)
     * @param authToken
     * @return An AnonymousResult object, do a getId on this object to get anonymousId
     */
    public AnonymousResult createAnonymous(String authToken) throws ApiException {
        return anonymous.create(authToken);
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
     * @param anonymous anonymous
     * @param request request
     * @return BlacklistUpdateResponse
     */
    public void bind(String anonymousId, String customerId, String authToken) throws ApiException {
        Customer customer = new Customer();
        customer.setCustomer(customerId);
        anonymous.update(anonymousId, customer, authToken);
    }


    /**
     * Binds an anonymous id to a customer, given an auth token
     * @author sudnya
     * @example
     * String token = attuneClient.getBinding(anonymousId, authToken)
     * @param authToken
     * @return An AnonymousResult object, do a getId on this object to get anonymousId
     */
    public Customer getBoundCustomer(String anonymousId, String authToken) throws ApiException {
        return anonymous.get(anonymousId, authToken);
    }

    /**
     * Returns a ranking of the specified entities for the current user, given an auth token
     * @author sudnya
     * @example
     *
     */
    public RankedEntities getRankings(RankingParams rankingParams, String authToken) throws ApiException {
        return entities.getRankings(rankingParams, authToken);
    }
}
