package io.swagger.client.model;

import io.swagger.client.model.ScopeEntry;
import java.util.*;
import io.swagger.client.model.Date-time;

import com.wordnik.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * 
 **/
@ApiModel(description = "")
public class BlacklistParams  {
  
  private Date-time activeFrom = null;
  private Date-time activeTo = null;
  private String entityType = null;
  private List<String> ids = new ArrayList<String>() ;
  private Boolean disabled = null;
  private List<ScopeEntry> scope = new ArrayList<ScopeEntry>() ;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("activeFrom")
  public Date-time getActiveFrom() {
    return activeFrom;
  }
  public void setActiveFrom(Date-time activeFrom) {
    this.activeFrom = activeFrom;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("activeTo")
  public Date-time getActiveTo() {
    return activeTo;
  }
  public void setActiveTo(Date-time activeTo) {
    this.activeTo = activeTo;
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
    sb.append("class BlacklistParams {\n");
    
    sb.append("  activeFrom: ").append(activeFrom).append("\n");
    sb.append("  activeTo: ").append(activeTo).append("\n");
    sb.append("  entityType: ").append(entityType).append("\n");
    sb.append("  ids: ").append(ids).append("\n");
    sb.append("  disabled: ").append(disabled).append("\n");
    sb.append("  scope: ").append(scope).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
