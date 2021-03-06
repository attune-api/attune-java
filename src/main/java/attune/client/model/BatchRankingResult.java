package attune.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;


/**
 * Array of ranking results.
 **/
@ApiModel(description = "Array of ranking results.")
public class BatchRankingResult  {
  
  private List<RankedEntities> results = new ArrayList<RankedEntities>() ;

  
  /**
   * Array of rankings in order of the parameters provided as input.
   **/
  @ApiModelProperty(value = "Array of rankings in order of the parameters provided as input.")
  @JsonProperty("results")
  public List<RankedEntities> getResults() {
    return results;
  }
  public void setResults(List<RankedEntities> results) {
    this.results = results;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class BatchRankingResult {\n");
    
    sb.append("  results: ").append(results).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
