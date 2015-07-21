package attune.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 
 **/
@ApiModel(description = "")
public class BlacklistParams  {
  
  private Date activeFrom = null;
  private Date activeTo = null;
  private String entityType = null;
  private List<String> ids = new ArrayList<String>() ;
  private Boolean disabled = null;
  private List<ScopeEntry> scope = new ArrayList<ScopeEntry>() ;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("activeFrom")
  public Date getActiveFrom() {
    return activeFrom;
  }
  public void setActiveFrom(Date activeFrom) {
    this.activeFrom = activeFrom;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("activeTo")
  public Date getActiveTo() {
    return activeTo;
  }
  public void setActiveTo(Date activeTo) {
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
