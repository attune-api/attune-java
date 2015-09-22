package attune.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;


/**
 * Array of the parameters specified for a ranking request
 **/
@ApiModel(description = "Array of the parameters specified for a ranking request")
public class BatchRankingRequest  {
  
  private List<RankingParams> requests = new ArrayList<RankingParams>() ;

  public BatchRankingRequest(BatchRankingRequest that) {

      List<RankingParams> temp = new ArrayList<>();
      for (RankingParams thatOne : that.getRequests()) {
          temp.add(new RankingParams(thatOne));
      }
      this.setRequests(temp);
  }
  
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
