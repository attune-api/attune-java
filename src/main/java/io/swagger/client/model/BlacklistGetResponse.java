package io.swagger.client.model;

import java.util.*;
import io.swagger.client.model.Blacklist;

import com.wordnik.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * 
 **/
@ApiModel(description = "")
public class BlacklistGetResponse  {
  
  private List<Blacklist> blacklists = new ArrayList<Blacklist>() ;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("blacklists")
  public List<Blacklist> getBlacklists() {
    return blacklists;
  }
  public void setBlacklists(List<Blacklist> blacklists) {
    this.blacklists = blacklists;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class BlacklistGetResponse {\n");
    
    sb.append("  blacklists: ").append(blacklists).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
