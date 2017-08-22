package uk.amccabe.searchfight.compare;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import uk.amccabe.searchfight.engine.SearchEngine;
import uk.amccabe.searchfight.search.QueryExecutor;

/**
 * Unit tests for the QueryComparator class.
 * 
 * @author alan.mccabe92@gmail.com
 *
 */
public class QueryComparatorTest {

  @Mock
  private QueryExecutor queryExecutor;

  private QueryComparator comparator;
  private Map<SearchEngine, Long> resultsMap;
  private SearchEngine engine;

  private static final String FIRST_QUERY = "firstQuery";
  private static final String SECOND_QUERY = "secondQuery";
  private static final Long FIRST_RESULT = 1L;
  private static final Long SECOND_RESULT = 2L;

  @Before
  public void setUp() {
    initMocks(this);

    comparator = new QueryComparator();
    comparator.setExecutor(queryExecutor);

    engine = new SearchEngine();
    resultsMap = new HashMap<>();
    resultsMap.put(engine, FIRST_RESULT);
  }

  /**
   * Test that the executeAndCompare() method executes the query and sets the winners as expected.
   */
  @Test
  public void testExecuteAndCompare() {
    when(queryExecutor.executeQueryForAllEngines(any(String.class), any(List.class)))
        .thenReturn(resultsMap);

    comparator.executeAndCompare(Arrays.asList(FIRST_QUERY), Arrays.asList(engine));

    assertEquals(Pair.of(FIRST_QUERY, FIRST_RESULT), comparator.getEngineWinners().get(engine));
    assertEquals(Pair.of(FIRST_QUERY, FIRST_RESULT), comparator.getOverallWinner());
  }

  /**
   * Test that if two queries are executed on a single engine, the query that returns the largest
   * number of results is set as the engine winner.
   */
  @Test
  public void testCompareEngineWinner() {
    Map<SearchEngine, Long> secondResultsMap = new HashMap<SearchEngine, Long>(resultsMap);
    secondResultsMap.replace(engine, SECOND_RESULT);

    when(queryExecutor.executeQueryForAllEngines(eq(FIRST_QUERY), any(List.class)))
        .thenReturn(resultsMap);
    when(queryExecutor.executeQueryForAllEngines(eq(SECOND_QUERY), any(List.class)))
        .thenReturn(secondResultsMap);

    comparator.executeAndCompare(Arrays.asList(FIRST_QUERY, SECOND_QUERY), Arrays.asList(engine));

    assertEquals(Pair.of(SECOND_QUERY, SECOND_RESULT), comparator.getEngineWinners().get(engine));
  }

  /**
   * Test that if two queries are executed on a single engine and the first returns a greater number
   * of results, the engine winner is not overwritten by the second query.
   */
  @Test
  public void testCompareEngineWinner_notUpdate() {
    Map<SearchEngine, Long> secondResultsMap = new HashMap<SearchEngine, Long>(resultsMap);
    secondResultsMap.replace(engine, SECOND_RESULT);

    when(queryExecutor.executeQueryForAllEngines(eq(FIRST_QUERY), any(List.class)))
        .thenReturn(resultsMap);
    when(queryExecutor.executeQueryForAllEngines(eq(SECOND_QUERY), any(List.class)))
        .thenReturn(secondResultsMap);

    comparator.executeAndCompare(Arrays.asList(SECOND_QUERY, FIRST_QUERY), Arrays.asList(engine));

    assertEquals(Pair.of(SECOND_QUERY, SECOND_RESULT), comparator.getEngineWinners().get(engine));
  }

  /**
   * Test that if two queries are executed on two engines, the query that returns the largest number
   * of results across all engines is set as the overall winner.
   */
  @Test
  public void testCompareOverallWinner() {
    SearchEngine secondEngine = new SearchEngine();
    Map<SearchEngine, Long> secondResultsMap = new HashMap<SearchEngine, Long>(resultsMap);
    secondResultsMap.replace(engine, SECOND_RESULT);

    secondResultsMap.put(secondEngine, SECOND_RESULT);
    resultsMap.put(secondEngine, SECOND_RESULT);

    when(queryExecutor.executeQueryForAllEngines(eq(FIRST_QUERY), any(List.class)))
        .thenReturn(resultsMap);
    when(queryExecutor.executeQueryForAllEngines(eq(SECOND_QUERY), any(List.class)))
        .thenReturn(secondResultsMap);

    comparator.executeAndCompare(Arrays.asList(FIRST_QUERY, SECOND_QUERY), Arrays.asList(engine));

    assertEquals(Pair.of(SECOND_QUERY, (SECOND_RESULT * 2)), comparator.getOverallWinner());
  }

  /**
   * Test that if two queries are executed on two engines and the first returns a greater number of
   * total results, the overall winner is not overwritten by the second query.
   */
  @Test
  public void testCompareOverallWinner_notUpdate() {
    SearchEngine secondEngine = new SearchEngine();
    Map<SearchEngine, Long> secondResultsMap = new HashMap<SearchEngine, Long>(resultsMap);
    secondResultsMap.replace(engine, SECOND_RESULT);

    secondResultsMap.put(secondEngine, SECOND_RESULT);
    resultsMap.put(secondEngine, 4L);

    when(queryExecutor.executeQueryForAllEngines(eq(FIRST_QUERY), any(List.class)))
        .thenReturn(resultsMap);
    when(queryExecutor.executeQueryForAllEngines(eq(SECOND_QUERY), any(List.class)))
        .thenReturn(secondResultsMap);

    comparator.executeAndCompare(Arrays.asList(FIRST_QUERY, SECOND_QUERY), Arrays.asList(engine));

    assertEquals(Pair.of(FIRST_QUERY, (FIRST_RESULT + 4L)), comparator.getOverallWinner());
  }

}
