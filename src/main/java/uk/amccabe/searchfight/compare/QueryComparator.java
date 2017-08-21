package uk.amccabe.searchfight.compare;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

import uk.amccabe.searchfight.engine.SearchEngine;
import uk.amccabe.searchfight.search.QueryExecutor;

public class QueryComparator {

  private static final Logger logger = Logger.getLogger(QueryComparator.class);

  private Map<SearchEngine, Pair<String, Long>> engineWinners;
  private Pair<String, Long> overallWinner;

  public QueryComparator() {
    engineWinners = new HashMap<>();
    overallWinner = null;
  }

  public void executeAndCompare(List<String> allQueries, List<SearchEngine> allEngines) {
    QueryExecutor executor = new QueryExecutor();

    StringBuilder resultsBuilder;
    for (String query : allQueries) {
      Map<SearchEngine, Long> allResults = executor.executeQueryForAllEngines(query, allEngines);
      compareOverallWinner(allResults, query);

      resultsBuilder = new StringBuilder();
      resultsBuilder.append(String.format("\n\n Results for: %s\n", query));

      for (SearchEngine engine : allEngines) {
        resultsBuilder
            .append(String.format("\n\t%s: %s", engine.getName(), allResults.get(engine)));

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

  private void compareOverallWinner(Map<SearchEngine, Long> allResults, String query) {
    Long totalResults = 0L;

    for (Long currentResult : allResults.values()) {
      totalResults += currentResult;
    }

    if (overallWinner == null || totalResults > overallWinner.getRight()) {
      overallWinner = Pair.of(query, totalResults);
    }
  }
}
