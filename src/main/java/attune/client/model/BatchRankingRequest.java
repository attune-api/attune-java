package attune.client.model;

import java.util.*;

import com.wordnik.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Array of the parameters specified for a ranking request
 **/
@ApiModel(description = "Array of the parameters specified for a ranking request")
public class BatchRankingRequest  {
  
  private List<RankingParams> requests = new ArrayList<RankingParams>() ;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("requests")
  public List<RankingParams> getRequests() {
    return requests;
  }
  public void setRequests(List<RankingParams> requests) {
    this.requests = requests;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class BatchRankingRequest {\n");
    
    sb.append("  requests: ").append(requests).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
