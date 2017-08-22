package uk.amccabe.searchfight.compare;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

import uk.amccabe.searchfight.engine.SearchEngine;
import uk.amccabe.searchfight.search.QueryExecutor;

/**
 * Class to execute all queries and compare the results. Execution of queries is delegated to an
 * instance of the @QueryExecutor class.
 * 
 * @author alan.mccabe92@gmail.com
 *
 */
public class QueryComparator {

  private static final Logger logger = Logger.getLogger(QueryComparator.class);

  private Map<SearchEngine, Pair<String, Long>> engineWinners;
  private Pair<String, Long> overallWinner;

  private QueryExecutor executor;

  public QueryComparator() {
    engineWinners = new HashMap<>();
    overallWinner = null;
    executor = new QueryExecutor();
  }

  /**
   * Execute all queries on all supplied search engines.
   * 
   * Queries are executed one by one on all search engines with results returned as a Map of Long
   * results keyed on SearchEngines instances. Each map of results is compared to the current top
   * query for each search engine as well as an overall "winning" query determined by the total
   * number of results across all search engines.
   * 
   * The results of each query are logged as they are received, with the winners for each search
   * engine as well as the overall winning query logged once all queries have been executed and
   * compared.
   * 
   * @param allQueries List of query strings to execute
   * @param allEngines List of SearchEngines to query
   */
  public void executeAndCompare(List<String> allQueries, List<SearchEngine> allEngines) {
    StringBuilder resultsBuilder;
    for (String query : allQueries) {
      Map<SearchEngine, Long> allResults = executor.executeQueryForAllEngines(query, allEngines);
      // Check the total number of results against the current "winning" query
      compareOverallWinner(allResults, query);

      resultsBuilder = new StringBuilder();
      resultsBuilder.append(String.format("\n\n Results for: %s\n", query));
      for (SearchEngine engine : allEngines) {
        resultsBuilder
            .append(String.format("\n\t%s: %s", engine.getName(), allResults.get(engine)));

        // Check the number of results for each engine against it's current "winning" query
        compareEngineWinner(engine, allResults.get(engine), query);
      }

      logger.info(resultsBuilder.toString());
    }

    StringBuilder overallWinnerBuilder = new StringBuilder();
    overallWinnerBuilder.append("\n\n All queries ran successfully.\n");
    for (SearchEngine engine : allEngines) {
      overallWinnerBuilder.append(String.format("\n\t%s winner: \t%s", engine.getName(),
          engineWinners.get(engine).getLeft()));
    }

    overallWinnerBuilder.append(String.format("\n\n Overall winner: %s", overallWinner.getLeft()));
    logger.info(overallWinnerBuilder.toString());
  }

  /**
   * Compare the result of a query with the current top query result for a certain search engine. If
   * the result is greater than the current winning query, replace the value in the HashMap.
   * 
   * @param engine SearchEngine the query was executed on
   * @param result Long result of the query
   * @param query String query that was executed on the SearchEngine
   */
  private void compareEngineWinner(SearchEngine engine, Long result, String query) {
    Pair<String, Long> currentWinner = engineWinners.get(engine);
    Pair<String, Long> winner;

    if (currentWinner == null || result > currentWinner.getRight()) {
      winner = Pair.of(query, result);
    } else {
      winner = currentWinner;
    }

    engineWinners.put(engine, winner);
  }

  /**
   * Total the number of results from a specific query and compare this to the current overall
   * leading query. If the totalled results is greater then replace it as the overall winner.
   * 
   * @param allResults
   * @param query
   */
  private void compareOverallWinner(Map<SearchEngine, Long> allResults, String query) {
    Long totalResults = 0L;

    for (Long currentResult : allResults.values()) {
      totalResults += currentResult;
    }

    if (overallWinner == null || totalResults > overallWinner.getRight()) {
      overallWinner = Pair.of(query, totalResults);
    }
  }

  public QueryExecutor getExecutor() {
    return executor;
  }

  public void setExecutor(QueryExecutor executor) {
    this.executor = executor;
  }

  public Map<SearchEngine, Pair<String, Long>> getEngineWinners() {
    return engineWinners;
  }

  public Pair<String, Long> getOverallWinner() {
    return overallWinner;
  }
}
