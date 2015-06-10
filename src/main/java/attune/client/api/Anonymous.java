package attune.client.api;

import attune.client.ApiException;
import attune.client.ApiInvoker;
import attune.client.model.AnonymousResult;
import attune.client.model.BlacklistUpdateResponse;
import attune.client.model.Customer;
import com.sun.jersey.multipart.FormDataMultiPart;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sudnya on 5/26/15.
 */
public class Anonymous {
    String basePath       = "https://api.attune-staging.co";
    ApiInvoker apiInvoker = ApiInvoker.getInstance();

    public ApiInvoker getInvoker() {
        return apiInvoker;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getBasePath() {
        return basePath;
    }
    /**
     * Create anonymous visitor
     * Create anonymous visitor
     * @return AnonymousResult
     */
    public AnonymousResult create (String auth_token) throws ApiException {
        Object postBody = null;


        // create path and map variables
        String path = "/anonymous".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();
        Map<String, String> formParams = new HashMap<String, String>();

        queryParams.put("access_token", auth_token);

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
            String response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams, postBody, headerParams, formParams, contentType);
            if(response != null){
                return (AnonymousResult) ApiInvoker.deserialize(response, "", AnonymousResult.class);
            }
            else {
                return null;
            }
        } catch (ApiException ex) {
            throw ex;
        }
    }

    /**
     * Returns an anonymous visitor, containing any assigned customer ID.
     * Returns an anonymous visitor, containing any assigned customer ID.
     * @param anonymous anonymous
     * @return Customer
     */
    public Customer get (String anonymous, String accessToken) throws ApiException {
        Object postBody = null;


        // create path and map variables
        String path = "/anonymous/{anonymous}".replaceAll("\\{format\\}","json")
                .replaceAll("\\{" + "anonymous" + "\\}", apiInvoker.escapeString(anonymous.toString()));

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();
        Map<String, String> formParams = new HashMap<String, String>();
        queryParams.put("access_token", accessToken);



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
            String response = apiInvoker.invokeAPI(basePath, path, "GET", queryParams, postBody, headerParams, formParams, contentType);
            if(response != null){
                return (Customer) ApiInvoker.deserialize(response, "", Customer.class);
            }
            else {
                return null;
            }
        } catch (ApiException ex) {
            throw ex;
        }
    }

    /**
     * Binds one actor to another.
     * Binds one actor to another, allowing activities of those actors to be shared between the two.
     * @param anonymous anonymous
     * @param request request
     * @return BlacklistUpdateResponse
     */
    public BlacklistUpdateResponse update (String anonymous, Customer request, String accessToken) throws ApiException {
        Object postBody = request;


        // create path and map variables
        String path = "/anonymous/{anonymous}".replaceAll("\\{format\\}","json")
                .replaceAll("\\{" + "anonymous" + "\\}", apiInvoker.escapeString(anonymous.toString()));

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();
        Map<String, String> formParams = new HashMap<String, String>();
        queryParams.put("access_token", accessToken);



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
            String response = apiInvoker.invokeAPI(basePath, path, "PUT", queryParams, postBody, headerParams, formParams, contentType);
            if(response.equals("")) {
                BlacklistUpdateResponse blacklistResponse = new BlacklistUpdateResponse();
                blacklistResponse.setResult(response);
                return blacklistResponse;
            } else if(response != null) {
                return (BlacklistUpdateResponse) ApiInvoker.deserialize(response, "", BlacklistUpdateResponse.class);
            }
            else {
                return null;
            }
        } catch (ApiException ex) {
            throw ex;
        }
    }

}
