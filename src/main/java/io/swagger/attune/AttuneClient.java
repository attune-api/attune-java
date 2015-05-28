package io.swagger.attune;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.wordnik.swagger.annotations.Api;
import io.swagger.attune.api.Anonymous;
import io.swagger.attune.api.Entities;
import io.swagger.attune.model.AnonymousResult;
import io.swagger.attune.model.Customer;
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

    public AnonymousResult createAnonymous(String accessToken) throws ApiException {
        return anonymous.create(accessToken);
    }

    public Customer getBinding(String anonymousId) throws ApiException {
        return anonymous.get(anonymousId);
    }
}
