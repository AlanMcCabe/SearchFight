package uk.amccabe.searchfight.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.amccabe.searchfight.engine.SearchEngine;

public class QueryExecutor {

  private QuerySearchEngine queryEngine;

  public QueryExecutor() {
    queryEngine = new QuerySearchEngine();
  }

  public Map<SearchEngine, Long> executeQueryForAllEngines(String queryString, List<SearchEngine> allEngines) {
    Map<SearchEngine, Long> allResults = new HashMap<>();
    
    for(SearchEngine engine : allEngines) {
      String result = queryEngine.executeQuery(queryString, engine);
      allResults.put(engine, parseResultString(result));
    }
    
    return allResults;
  }
  
  public Map<String, Long> executeAllQueries(List<String> allQueries, SearchEngine engine) {
    Map<String, Long> allResults = new HashMap<>();

    for (String query : allQueries) {
      String result = queryEngine.executeQuery(query, engine);
      allResults.put(query, parseResultString(result));
    }
    
    return allResults;
  }

  private long parseResultString(String result) {
    String sanitizedText = result.replaceAll("[^\\d]", "");

    return Long.parseLong(sanitizedText);
  }

}
