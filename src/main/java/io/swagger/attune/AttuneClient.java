package io.swagger.attune;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import io.swagger.attune.api.Anonymous;
import io.swagger.attune.api.Entities;
import io.swagger.attune.model.AnonymousResult;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import sun.applet.AppletIOException;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by sudnya on 5/27/15.
 */
public class AttuneClient {
    private AttuneConfigurable attuneConfigurable;
    private WebResource webResource;
    private Entities entities;
    private Anonymous anonymous;


    /** Initializes a new Client
    #
            # @example
    #   client = Attune::Client.new(
            #     endpoint: "http://example.com:8080/",
            #     timeout:  10
            #   )
            #
            # @param [Hash] options Options for connection (see Attune::Configurable)
    # @return A new client object
    */
    public AttuneClient() {
        attuneConfigurable = new AttuneDefault();
        entities  = new Entities();
        anonymous = new Anonymous();
    }

    /**    # Request an auth token
     #
     # @example Generate a new auth token
     #   token = client.get_auth_token("client id", "secret")
     # @param [String] client_id The client identifier.
     # @param [String] client_secret The secret key for the client.
     # @return An auth token if credentials accepted
     # @raise [ArgumentError] if client_id or client_secret is not provided
     # @raise [AuthenticationException] if client_id or client_secret are not accepted
     # @raise [Faraday::Error::ClientError] if the request fails or exceeds the timeout
     */
    public String getAuthToken(String clientId, String clientSecret) throws ApiException {
        if (clientId == null)
            throw new IllegalArgumentException("clientId is required");
        if (clientSecret == null)
            throw new IllegalArgumentException("clientSecret is required");

        Client client = Client.create(new DefaultClientConfig());
        WebResource authResource = client.resource(attuneConfigurable.endpoint + "/oauth/token");

        MultivaluedMap formData = new MultivaluedMapImpl();
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("grant_type", "client_credentials");
        ClientResponse response =
                authResource.type(MediaType.
                        APPLICATION_FORM_URLENCODED_TYPE)
                        .post(ClientResponse.class, formData);

        String body = response.getEntity(String.class);

        String access_token = null;

        try {
            JSONObject json = new JSONObject(body);

             access_token = json.getString("access_token");
        }
        catch (JSONException e) {
            throw new ApiException();
        }

        return access_token;
    }

    public AnonymousResult createAnonymous(String access_token) throws ApiException {
        return anonymous.create(access_token);
    }

}
