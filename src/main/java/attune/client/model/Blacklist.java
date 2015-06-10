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
public class Blacklist  {
  
  private List<String> ids = new ArrayList<String>() ;
  private String consumer = null;
  private String entityType = null;
  private Date startDate = null;
  private Date endDate = null;
  private Date createdDate = null;
  private Date updatedDate = null;
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
  public Date getStartDate() {
    return startDate;
  }
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("endDate")
  public Date getEndDate() {
    return endDate;
  }
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("createdDate")
  public Date getCreatedDate() {
    return createdDate;
  }
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("updatedDate")
  public Date getUpdatedDate() {
    return updatedDate;
  }
  public void setUpdatedDate(Date updatedDate) {
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
