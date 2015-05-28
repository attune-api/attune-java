package io.swagger.attune.api;

import io.swagger.attune.ApiInvoker;
import io.swagger.attune.ApiException;

import io.swagger.attune.model.AnonymousResult;
import io.swagger.attune.model.Customer;
import io.swagger.attune.model.BlacklistUpdateResponse;
import io.swagger.attune.model.RankingParams;
import io.swagger.attune.model.RankedEntities;
import io.swagger.attune.model.BatchRankingRequest;
import io.swagger.attune.model.BatchRankingResult;
import io.swagger.attune.model.BlacklistGetResponse;
import io.swagger.attune.model.BlacklistSaveResponse;
import io.swagger.attune.model.BlacklistParams;
import io.swagger.attune.model.Blacklist;
import io.swagger.attune.model.BlacklistDeleteResponse;

import com.sun.jersey.multipart.FormDataMultiPart;

import java.util.Map;
import java.util.HashMap;

public class AttuneRankingApi {
  String basePath = "https://api.attune-staging.co"; //"http://localhost/";
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
  public AnonymousResult create () throws ApiException {
    Object postBody = null;
    

    // create path and map variables
    String path = "/anonymous".replaceAll("\\{format\\}","json");

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
  public Customer get (String anonymous) throws ApiException {
    Object postBody = null;
    

    // create path and map variables
    String path = "/anonymous/{anonymous}".replaceAll("\\{format\\}","json")
      .replaceAll("\\{" + "anonymous" + "\\}", apiInvoker.escapeString(anonymous.toString()));

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
  public BlacklistUpdateResponse update (String anonymous, Customer request) throws ApiException {
    Object postBody = request;
    

    // create path and map variables
    String path = "/anonymous/{anonymous}".replaceAll("\\{format\\}","json")
      .replaceAll("\\{" + "anonymous" + "\\}", apiInvoker.escapeString(anonymous.toString()));

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
      String response = apiInvoker.invokeAPI(basePath, path, "PUT", queryParams, postBody, headerParams, formParams, contentType);
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
   * Returns a ranking of the specified entities for the current user.
   * Returns a ranking of the specified entities for the current user.
   * @param params params
   * @return RankedEntities
   */
  public RankedEntities getRankings (RankingParams params) throws ApiException {
    Object postBody = params;
    

    // create path and map variables
    String path = "/entities/ranking".replaceAll("\\{format\\}","json");

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
      String response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams, postBody, headerParams, formParams, contentType);
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
  public BatchRankingResult batchGetRankings (BatchRankingRequest batchRequest) throws ApiException {
    Object postBody = batchRequest;
    

    // create path and map variables
    String path = "/entities/ranking/many".replaceAll("\\{format\\}","json");

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
      String response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams, postBody, headerParams, formParams, contentType);
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
      String response = apiInvoker.invokeAPI(basePath, path, "GET", queryParams, postBody, headerParams, formParams, contentType);
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
      String response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams, postBody, headerParams, formParams, contentType);
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
      String response = apiInvoker.invokeAPI(basePath, path, "GET", queryParams, postBody, headerParams, formParams, contentType);
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
      String response = apiInvoker.invokeAPI(basePath, path, "PUT", queryParams, postBody, headerParams, formParams, contentType);
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
      String response = apiInvoker.invokeAPI(basePath, path, "DELETE", queryParams, postBody, headerParams, formParams, contentType);
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
