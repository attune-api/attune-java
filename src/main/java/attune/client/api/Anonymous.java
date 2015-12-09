package attune.client.api;

import attune.client.ApiException;
import attune.client.ApiInvoker;
import attune.client.AttuneConfigurable;
import attune.client.Version;
import attune.client.model.AnonymousResult;
import attune.client.model.BlacklistUpdateResponse;
import attune.client.model.Customer;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import javax.ws.rs.core.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sudnya on 5/26/15.
 */
public class Anonymous {
    AttuneConfigurable attuneConfig;
    ApiInvoker apiInvoker = ApiInvoker.getInstance();

    public Anonymous(AttuneConfigurable attuneConfig) {
        this.attuneConfig = attuneConfig;
    }

    public void updateDefaultConfig(AttuneConfigurable attuneConfig) {
        this.attuneConfig = attuneConfig;
    }

    public ApiInvoker getInvoker() {
        return apiInvoker;
    }

    /**
     * Create anonymous visitor
     * @param accessToken authentication token
     * @return AnonymousResult
     */
    public AnonymousResult create (String accessToken) throws ApiException {
        Object postBody = null;


        // create path and map variables
        String path = "/anonymous";

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        headerParams.put(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

        String[] contentTypes = {

        };

        String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

        if(contentType.startsWith("multipart/form-data")) {
            boolean hasFields = false;
            FormDataMultiPart mp = new FormDataMultiPart();

            if(hasFields)
                postBody = mp;
        }
        else {

        }

        try {
            String response = apiInvoker.invokeAPI(attuneConfig, path, "POST", queryParams, postBody, headerParams, contentType, Version.clientVersion);
            if(response != null){
                return (AnonymousResult) ApiInvoker.deserialize(response, "", AnonymousResult.class);
            } else {
                throw new ApiException(503, "Create anonymous response returned = null");
            }
        } catch (ApiException ex) {
        	ex.printStackTrace();
            throw new ApiException(ex.getCode(), "Exception in creating auth token");
        }
    }

    /**
     * Returns an anonymous visitor, containing any assigned customer ID.
     * @param anonymous anonymous
     * @param accessToken authentication token
     * @return Customer
     */
    public Customer get (String anonymous, String accessToken) throws ApiException {
        Object postBody = null;


        // create path and map variables
        String path = "/anonymous/" + apiInvoker.escapeString(anonymous.toString());

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();
        headerParams.put(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);



        String[] contentTypes = {

        };

        String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

        if(contentType.startsWith("multipart/form-data")) {
            boolean hasFields = false;
            FormDataMultiPart mp = new FormDataMultiPart();

            if(hasFields)
                postBody = mp;
        }
        else {

        }

        try {
            String response = apiInvoker.invokeAPI(attuneConfig, path, "GET", queryParams, postBody, headerParams, contentType, Version.clientVersion);
            if(response != null){
                return (Customer) ApiInvoker.deserialize(response, "", Customer.class);
            }
            else {
                throw new ApiException(503, "Response returned null");
            }
        } catch (ApiException ex) {
            throw new ApiException(ex.getCode(), "Exception occurred when trying to retrieve customer id for anonymous: " + anonymous);
        }
    }

    /**
     * Binds one actor to another, allowing activities of those actors to be shared between the two.
     * @param anonymous anonymous
     * @param request request
     * @return BlacklistUpdateResponse
     */
    public BlacklistUpdateResponse update (String anonymous, Customer request, String accessToken) throws ApiException {
        Object postBody = request;


        // create path and map variables
        String path = "/anonymous/" + apiInvoker.escapeString(anonymous.toString());

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();
        headerParams.put(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);


        String contentType = "application/json";

        try {
            String response = apiInvoker.invokeAPI(attuneConfig, path, "PUT", queryParams, postBody, headerParams, contentType, Version.clientVersion);
            if (response != null) {
                if (response.equals("")) {
                    BlacklistUpdateResponse blacklistResponse = new BlacklistUpdateResponse();
                    blacklistResponse.setResult(response);
                    return blacklistResponse;
                }
                return (BlacklistUpdateResponse) ApiInvoker.deserialize(response, "", BlacklistUpdateResponse.class);
            } else {
                throw new ApiException(503, "Response returned null");
            }
        } catch (ApiException ex) {
            throw new ApiException(ex.getCode(), "Exception occurred when trying to bind customer: " + request.getCustomer() + " to anonymous: " + anonymous);
        }
    }

}
