package io.swagger.attune.model;

import java.util.*;

import com.wordnik.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * List of ids in ranked order. If an error occurs, returns message and status code.
 **/
@ApiModel(description = "List of ids in ranked order. If an error occurs, returns message and status code.")
public class RankedEntities  {
  
  private List<String> ranking = new ArrayList<String>() ;
  private String cell = null;
  private Integer status = null;
  private String message = null;

  
  /**
   * List of ids in ranked order
   **/
  @ApiModelProperty(value = "List of ids in ranked order")
  @JsonProperty("ranking")
  public List<String> getRanking() {
    return ranking;
  }
  public void setRanking(List<String> ranking) {
    this.ranking = ranking;
  }

  
  /**
   * Cell assignment
   **/
  @ApiModelProperty(value = "Cell assignment")
  @JsonProperty("cell")
  public String getCell() {
    return cell;
  }
  public void setCell(String cell) {
    this.cell = cell;
  }

  
  /**
   * HTTP status code if ranking failed
   **/
  @ApiModelProperty(value = "HTTP status code if ranking failed")
  @JsonProperty("status")
  public Integer getStatus() {
    return status;
  }
  public void setStatus(Integer status) {
    this.status = status;
  }

  
  /**
   * Error message if ranking failed
   **/
  @ApiModelProperty(value = "Error message if ranking failed")
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class RankedEntities {\n");
    
    sb.append("  ranking: ").append(ranking).append("\n");
    sb.append("  cell: ").append(cell).append("\n");
    sb.append("  status: ").append(status).append("\n");
    sb.append("  message: ").append(message).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
