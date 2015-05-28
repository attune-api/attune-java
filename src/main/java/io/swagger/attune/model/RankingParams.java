package io.swagger.attune.model;

import java.util.*;

import com.wordnik.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Inputs for ranking a set of ids for a particular user.
 **/
@ApiModel(description = "Inputs for ranking a set of ids for a particular user.")
public class RankingParams  {
  
  private String view = null;
  private String userAgent = null;
  private String anonymous = null;
  private String ip = null;
  private String entityType = null;
  private List<String> ids = new ArrayList<String>() ;
  private String application = null;
  private List<Integer> quantities = new ArrayList<Integer>() ;
  private String customer = null;
  private List<ScopeEntry> scope = new ArrayList<ScopeEntry>() ;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("view")
  public String getView() {
    return view;
  }
  public void setView(String view) {
    this.view = view;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("userAgent")
  public String getUserAgent() {
    return userAgent;
  }
  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("anonymous")
  public String getAnonymous() {
    return anonymous;
  }
  public void setAnonymous(String anonymous) {
    this.anonymous = anonymous;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("ip")
  public String getIp() {
    return ip;
  }
  public void setIp(String ip) {
    this.ip = ip;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("entityType")
  public String getEntityType() {
    return entityType;
  }
  public void setEntityType(String entityType) {
    this.entityType = entityType;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("ids")
  public List<String> getIds() {
    return ids;
  }
  public void setIds(List<String> ids) {
    this.ids = ids;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("application")
  public String getApplication() {
    return application;
  }
  public void setApplication(String application) {
    this.application = application;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("quantities")
  public List<Integer> getQuantities() {
    return quantities;
  }
  public void setQuantities(List<Integer> quantities) {
    this.quantities = quantities;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("customer")
  public String getCustomer() {
    return customer;
  }
  public void setCustomer(String customer) {
    this.customer = customer;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("scope")
  public List<ScopeEntry> getScope() {
    return scope;
  }
  public void setScope(List<ScopeEntry> scope) {
    this.scope = scope;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class RankingParams {\n");
    
    sb.append("  view: ").append(view).append("\n");
    sb.append("  userAgent: ").append(userAgent).append("\n");
    sb.append("  anonymous: ").append(anonymous).append("\n");
    sb.append("  ip: ").append(ip).append("\n");
    sb.append("  entityType: ").append(entityType).append("\n");
    sb.append("  ids: ").append(ids).append("\n");
    sb.append("  application: ").append(application).append("\n");
    sb.append("  quantities: ").append(quantities).append("\n");
    sb.append("  customer: ").append(customer).append("\n");
    sb.append("  scope: ").append(scope).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
