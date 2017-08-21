package uk.amccabe.searchfight.search.bing;

import uk.amccabe.searchfight.search.QueryEngine;

public class QueryBing extends QueryEngine {

  public QueryBing() {
    this.engineUrl = "http://www.bing.com/search?q=";
    this.selectResultStats = "span.sb_count";
  }

  @Override
  public long query(String queryString) {
    return executeQuery(queryString);
  }

}
