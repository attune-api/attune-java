package io.swagger.client.model;

import java.util.*;
import io.swagger.client.model.Date-time;

import com.wordnik.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * 
 **/
@ApiModel(description = "")
public class Blacklist  {
  
  private List<String> ids = new ArrayList<String>() ;
  private String consumer = null;
  private String entityType = null;
  private Date-time startDate = null;
  private Date-time endDate = null;
  private Date-time createdDate = null;
  private Date-time updatedDate = null;
  private String scope = null;
  private Boolean disabled = null;
  private String id = null;

  
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
  @JsonProperty("consumer")
  public String getConsumer() {
    return consumer;
  }
  public void setConsumer(String consumer) {
    this.consumer = consumer;
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
  @JsonProperty("startDate")
  public Date-time getStartDate() {
    return startDate;
  }
  public void setStartDate(Date-time startDate) {
    this.startDate = startDate;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("endDate")
  public Date-time getEndDate() {
    return endDate;
  }
  public void setEndDate(Date-time endDate) {
    this.endDate = endDate;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("createdDate")
  public Date-time getCreatedDate() {
    return createdDate;
  }
  public void setCreatedDate(Date-time createdDate) {
    this.createdDate = createdDate;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("updatedDate")
  public Date-time getUpdatedDate() {
    return updatedDate;
  }
  public void setUpdatedDate(Date-time updatedDate) {
    this.updatedDate = updatedDate;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("scope")
  public String getScope() {
    return scope;
  }
  public void setScope(String scope) {
    this.scope = scope;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("disabled")
  public Boolean getDisabled() {
    return disabled;
  }
  public void setDisabled(Boolean disabled) {
    this.disabled = disabled;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("id")
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Blacklist {\n");
    
    sb.append("  ids: ").append(ids).append("\n");
    sb.append("  consumer: ").append(consumer).append("\n");
    sb.append("  entityType: ").append(entityType).append("\n");
    sb.append("  startDate: ").append(startDate).append("\n");
    sb.append("  endDate: ").append(endDate).append("\n");
    sb.append("  createdDate: ").append(createdDate).append("\n");
    sb.append("  updatedDate: ").append(updatedDate).append("\n");
    sb.append("  scope: ").append(scope).append("\n");
    sb.append("  disabled: ").append(disabled).append("\n");
    sb.append("  id: ").append(id).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
