package attune.client.api;

import attune.client.ApiException;
import attune.client.ApiInvoker;
import attune.client.AttuneConfigurable;
import attune.client.Version;
import attune.client.model.*;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sudnya on 5/26/15.
 */
public class Entities {
    AttuneConfigurable attuneConfig;
    ApiInvoker apiInvoker = ApiInvoker.getInstance();

    public Entities(AttuneConfigurable attuneConfig) {
        this.attuneConfig = attuneConfig;
    }

    public ApiInvoker getInvoker() {
        return apiInvoker;
    }

    /**
     * Returns a ranking of the specified entities for the current user.
     * Returns a ranking of the specified entities for the current user.
     * @param params params
     * @return RankedEntities
     */
    public RankedEntities getRankings (RankingParams params, String accessToken) throws ApiException {
        Object postBody = params;


        // create path and map variables
        String path = "/entities/ranking".replaceAll("\\{format\\}","json");

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
            String response = apiInvoker.invokeAPI(attuneConfig, path, "POST", queryParams, postBody, headerParams, formParams, contentType, Version.clientVersion);
            if(response != null){
                return (RankedEntities) ApiInvoker.deserialize(response, "", RankedEntities.class);
            }
            else {
                return null;
            }
        } catch (ApiException ex) {
            throw ex;
        }
    }

    /**
     * Returns multiple rankings of the specified entities for the current user.
     * Returns multiple rankings of the specified entities for the current user.
     * @param batchRequest batchRequest
     * @return BatchRankingResult
     */
    public BatchRankingResult batchGetRankings (BatchRankingRequest batchRequest, String accessToken) throws ApiException {
        Object postBody = batchRequest;


        // create path and map variables
        String path = "/entities/ranking/many".replaceAll("\\{format\\}","json");

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
            String response = apiInvoker.invokeAPI(attuneConfig, path, "POST", queryParams, postBody, headerParams, formParams, contentType, Version.clientVersion);
            if(response != null){
                return (BatchRankingResult) ApiInvoker.deserialize(response, "", BatchRankingResult.class);
            }
            else {
                return null;
            }
        } catch (ApiException ex) {
            throw ex;
        }
    }

    /**
     * List all blacklist entries for your account.
     * List all blacklist entries for your account.
     * @return BlacklistGetResponse
     */
    public BlacklistGetResponse blacklistGetAll () throws ApiException {
        Object postBody = null;


        // create path and map variables
        String path = "/entities/rankings/blacklists".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();
        Map<String, String> formParams = new HashMap<String, String>();



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
            String response = apiInvoker.invokeAPI(attuneConfig, path, "GET", queryParams, postBody, headerParams, formParams, contentType, Version.clientVersion);
            if(response != null){
                return (BlacklistGetResponse) ApiInvoker.deserialize(response, "", BlacklistGetResponse.class);
            }
            else {
                return null;
            }
        } catch (ApiException ex) {
            throw ex;
        }
    }

    /**
     * Save a new blacklist entry.
     * Save a new blacklist entry.
     * @param params params
     * @return BlacklistSaveResponse
     */
    public BlacklistSaveResponse blacklistSave (BlacklistParams params) throws ApiException {
        Object postBody = params;


        // create path and map variables
        String path = "/entities/rankings/blacklists".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();
        Map<String, String> formParams = new HashMap<String, String>();



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
            String response = apiInvoker.invokeAPI(attuneConfig, path, "POST", queryParams, postBody, headerParams, formParams, contentType, Version.clientVersion);
            if(response != null){
                return (BlacklistSaveResponse) ApiInvoker.deserialize(response, "", BlacklistSaveResponse.class);
            }
            else {
                return null;
            }
        } catch (ApiException ex) {
            throw ex;
        }
    }

    /**
     * Returns the blacklist with the specified ID.
     * Returns the blacklist with the specified ID.
     * @param id id
     * @return Blacklist
     */
    public Blacklist blacklistGet (String id) throws ApiException {
        Object postBody = null;


        // create path and map variables
        String path = "/entities/rankings/blacklists/{id}".replaceAll("\\{format\\}","json")
                .replaceAll("\\{" + "id" + "\\}", apiInvoker.escapeString(id.toString()));

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();
        Map<String, String> formParams = new HashMap<String, String>();



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
            String response = apiInvoker.invokeAPI(attuneConfig, path, "GET", queryParams, postBody, headerParams, formParams, contentType, Version.clientVersion);
            if(response != null){
                return (Blacklist) ApiInvoker.deserialize(response, "", Blacklist.class);
            }
            else {
                return null;
            }
        } catch (ApiException ex) {
            throw ex;
        }
    }

    /**
     * Updates a blacklist with the specified ID.
     * Updates a blacklist with the specified ID.
     * @param id id
     * @param params params
     * @return BlacklistUpdateResponse
     */
    public BlacklistUpdateResponse blacklistUpdate (String id, BlacklistParams params) throws ApiException {
        Object postBody = params;


        // create path and map variables
        String path = "/entities/rankings/blacklists/{id}".replaceAll("\\{format\\}","json")
                .replaceAll("\\{" + "id" + "\\}", apiInvoker.escapeString(id.toString()));

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();
        Map<String, String> formParams = new HashMap<String, String>();



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
            String response = apiInvoker.invokeAPI(attuneConfig, path, "PUT", queryParams, postBody, headerParams, formParams, contentType, Version.clientVersion);
            if(response != null){
                return (BlacklistUpdateResponse) ApiInvoker.deserialize(response, "", BlacklistUpdateResponse.class);
            }
            else {
                return null;
            }
        } catch (ApiException ex) {
            throw ex;
        }
    }

    /**
     * Delete blacklist by id.
     * Delete blacklist by id.
     * @param id id
     * @return BlacklistDeleteResponse
     */
    public BlacklistDeleteResponse blacklistDelete (String id) throws ApiException {
        Object postBody = null;


        // create path and map variables
        String path = "/entities/rankings/blacklists/{id}".replaceAll("\\{format\\}","json")
                .replaceAll("\\{" + "id" + "\\}", apiInvoker.escapeString(id.toString()));

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();
        Map<String, String> formParams = new HashMap<String, String>();



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
            String response = apiInvoker.invokeAPI(attuneConfig, path, "DELETE", queryParams, postBody, headerParams, formParams, contentType, Version.clientVersion);
            if(response != null){
                return (BlacklistDeleteResponse) ApiInvoker.deserialize(response, "", BlacklistDeleteResponse.class);
            }
            else {
                return null;
            }
        } catch (ApiException ex) {
            throw ex;
        }
    }

}
