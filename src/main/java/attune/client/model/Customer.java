package attune.client.model;


import com.wordnik.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Customer associated with an anonymous user
 **/
@ApiModel(description = "Customer associated with an anonymous user")
public class Customer  {
  
  private String customer = null;

  
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

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Customer {\n");
    
    sb.append("  customer: ").append(customer).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
