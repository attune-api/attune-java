package attune.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;


/**
 * Inputs for ranking a set of ids for a particular user.
 **/
@ApiModel(description = "Inputs for ranking a set of ids for a particular user.")
public class RankingParams implements Cloneable {
  
  private String view = null;
  private String userAgent = null;
  private String anonymous = null;
  private String ip = null;
  private String entityType = null;
  private String entitySource = "ids";
  private String application = null;
  private String customer = null;
  private List<String> ids = new ArrayList<String>() ;
  private List<Integer> quantities = new ArrayList<Integer>() ;
  private String scope = null;

  public RankingParams() {}

  //deep copy
  public RankingParams(RankingParams that) {
      this.view          = that.getView();
      this.userAgent     = that.getUserAgent();
      this.anonymous     = that.getAnonymous();
      this.ip            = that.getIp();
      this.entityType    = that.getEntityType();
      this.entitySource  = that.getEntitySource();
      this.application   = that.getApplication();
      this.customer      = that.getCustomer();
      this.scope         = that.getScope();

      this.ids.addAll(that.getIds());
      this.quantities.addAll(that.getQuantities());
  }

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
  @JsonProperty("entitySource")
  public String getEntitySource() {
      return entitySource;
  }

  public void setEntitySource(String entitySource) {
      this.entitySource = entitySource;
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
  public String getScope() {
    return scope;
  }
  public void setScope(String scope) {
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
