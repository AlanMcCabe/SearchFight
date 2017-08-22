package uk.amccabe.searchfight.search;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import uk.amccabe.searchfight.engine.SearchEngine;

/**
 * Unit tests for the QueryExecutor class.
 * 
 * @author alan.mccabe92@gmail.com
 *
 */
public class QueryExecutorTest {

  @Mock
  private QuerySearchEngine queryEngine;

  @InjectMocks
  private QueryExecutor executor;

  private static final String VALID_RESPONSE = "About 12345 results";
  private static final String INVALID_RESPONSE = "No results";
  private static final String EMPTY_RESPONSE = "";
  private static final String QUERY = "query";
  private SearchEngine searchEngine;

  @Before
  public void setUp() {
    initMocks(this);
    executor = new QueryExecutor();
    executor.setQueryEngine(queryEngine);

    searchEngine = new SearchEngine();
    searchEngine.setName("name");
  }

  /**
   * Test that if the executeQueryForAllEngines method is called for a valid search term and engine
   * list, the response is parsed into the number of results and returned.
   */
  @Test
  public void testExecuteQueryForAllEngines() {
    when(queryEngine.executeQuery(any(String.class), any(SearchEngine.class)))
        .thenReturn(VALID_RESPONSE);

    Map<SearchEngine, Long> results =
        executor.executeQueryForAllEngines(VALID_RESPONSE, Arrays.asList(searchEngine));

    assertEquals(1, results.size());
    assertEquals(12345L, results.get(searchEngine).longValue());
  }

  /**
   * Test that if the executeAllQueries method is called for a valid engine and search term list,
   * the response is parsed into the number of results and returned.
   */
  @Test
  public void testExecuteAllQueries() {
    when(queryEngine.executeQuery(any(String.class), any(SearchEngine.class)))
        .thenReturn(VALID_RESPONSE);

    Map<String, Long> results = executor.executeAllQueries(Arrays.asList(QUERY), searchEngine);

    assertEquals(1, results.size());
    assertEquals(12345L, results.get(QUERY).longValue());
  }

  /**
   * Test that if an invalid response is returned from the query engine, a value of 0 for the number
   * of results is returned.
   */
  @Test
  public void testExecuteAllQueries_invalidResponse() {
    when(queryEngine.executeQuery(any(String.class), any(SearchEngine.class)))
        .thenReturn(INVALID_RESPONSE);

    Map<String, Long> results = executor.executeAllQueries(Arrays.asList(QUERY), searchEngine);

    assertEquals(1, results.size());
    assertEquals(0L, results.get(QUERY).longValue());
  }

  /**
   * Test that if an empty response is returned from the query engine, a value of 0 for the number
   * of results is returned.
   */
  @Test
  public void testExecuteAllQueries_emptyResponse() {
    when(queryEngine.executeQuery(any(String.class), any(SearchEngine.class)))
        .thenReturn(EMPTY_RESPONSE);

    Map<String, Long> results = executor.executeAllQueries(Arrays.asList(QUERY), searchEngine);

    assertEquals(1, results.size());
    assertEquals(0L, results.get(QUERY).longValue());
  }

}
