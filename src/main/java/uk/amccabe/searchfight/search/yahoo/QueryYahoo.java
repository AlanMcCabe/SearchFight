package uk.amccabe.searchfight.search.yahoo;

import uk.amccabe.searchfight.search.QueryEngine;

public class QueryYahoo extends QueryEngine {
  
  public QueryYahoo() {
    this.engineUrl = "http://search.yahoo.com/search?q=";
    this.selectResultStats = "div.compPagination span";
  }

  @Override
  public long query(String queryString) {
    return executeQuery(queryString);
  }

}
