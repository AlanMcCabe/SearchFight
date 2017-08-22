package uk.amccabe.searchfight.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.amccabe.searchfight.engine.SearchEngine;

/**
 * Class to execute supplied queries on search engines as provided by the configuration.
 * 
 * As the result is returned as a String in the format e.g "About 1,000 results" it is
 * necessary to strip out all non-numeric characters before returning the results.
 * 
 * @author alan.mccabe92@gmail.com
 *
 */
public class QueryExecutor {

  private QuerySearchEngine queryEngine;

  public QueryExecutor() {
    queryEngine = new QuerySearchEngine();
  }

/**
 * Execute a single query on all available search engines and return a map of results keyed on the engine.
 * 
 * @param queryString Query to be executed by each engine
 * @param allEngines All search engines to execute the query on
 * @return HashMap of Long results keyed on SearchEngine engines
 */
  public Map<SearchEngine, Long> executeQueryForAllEngines(String queryString, List<SearchEngine> allEngines) {
    Map<SearchEngine, Long> allResults = new HashMap<>();
    
    for(SearchEngine engine : allEngines) {
      String result = queryEngine.executeQuery(queryString, engine);
      allResults.put(engine, parseResultString(result));
    }
    
    return allResults;
  }
  
  /**
   * Execute a list of queries on a single search engine and return a map of results keyed on the query.
   * 
   * @param allQueries List of queries to be executed
   * @param engine SearchEngine to execute the query on
   * @return HashMap of Long results keyed on query Strings.
   */
  public Map<String, Long> executeAllQueries(List<String> allQueries, SearchEngine engine) {
    Map<String, Long> allResults = new HashMap<>();

    for (String query : allQueries) {
      String result = queryEngine.executeQuery(query, engine);
      allResults.put(query, parseResultString(result));
    }
    
    return allResults;
  }

  /**
   * Helper method to strip all non-numeric characters out of the result string.
   * 
   * @param result Result string extracted from the query response
   * @return long value parsed from result
   */
  private long parseResultString(String result) {
    String sanitizedText = result.replaceAll("[^\\d]", "");

    return Long.parseLong(sanitizedText);
  }

}
