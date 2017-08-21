package uk.amccabe.searchfight.search.google;

import uk.amccabe.searchfight.search.QueryEngine;

/**
 * 
 * @author alan.mccabe92@gmail.com
 *
 */
public class QueryGoogle extends QueryEngine {

  public QueryGoogle() {
    this.engineUrl = "http://www.google.com/search?q=";
    this.selectResultStats = "div#resultStats";
  }

  @Override
  public long query(String queryString) {
    return executeQuery(queryString);
  }

}
