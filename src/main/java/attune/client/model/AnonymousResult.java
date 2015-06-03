package attune.client.model;


import com.wordnik.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Result of creating an anonymous user.
 **/
@ApiModel(description = "Result of creating an anonymous user.")
public class AnonymousResult  {
  
  private String id = null;

  
  /**
   * Id of the anonymous user.
   **/
  @ApiModelProperty(value = "Id of the anonymous user.")
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
    sb.append("class AnonymousResult {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
