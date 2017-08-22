package uk.amccabe.searchfight.search;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import uk.amccabe.searchfight.engine.SearchEngine;

/**
 * Unit tests for the QuerySearchEngine class.
 * 
 * @author alan.mccabe92@gmail.com
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Jsoup.class)
public class QuerySearchEngineTest {

  @Mock
  private Connection mockConnect;

  @Mock
  private Document mockDocument;

  @Mock
  private Element mockElement;

  @Mock
  private Elements mockElements;

  private QuerySearchEngine queryEngine;

  private SearchEngine searchEngine;

  private static final String RESPONSE_STRING = "response";
  private static final String QUERY = "query";

  @Before
  public void setUp() throws IOException {
    initMocks(this);
    queryEngine = new QuerySearchEngine();
    searchEngine = new SearchEngine();
    searchEngine.setName("name");
    searchEngine.setSearchUrl("url");
    searchEngine.setSearchUrl("selector");

    mockStatic(Jsoup.class);
    when(Jsoup.connect(any(String.class))).thenReturn(mockConnect);
    when(mockConnect.get()).thenReturn(mockDocument);
    when(mockConnect.userAgent(any(String.class))).thenReturn(mockConnect);
    when(mockDocument.select(any(String.class))).thenReturn(mockElements);
    when(mockElements.first()).thenReturn(mockElement);
    when(mockElement.text()).thenReturn(RESPONSE_STRING);
  }

  /**
   * Test that if the query is executed correctly, the response string is extracted and returned.
   */
  @Test
  public void testExecuteQuery() {
    assertEquals(RESPONSE_STRING, queryEngine.executeQuery(QUERY, searchEngine));
  }

  /**
   * Test that if an exception is thrown by JSoup e.g an IllegalArgumentException due to a malformed
   * URL, an empty String is returned.
   * 
   * @throws IOException
   */
  @Test
  public void testExecuteQuery_malformedURL() throws IOException {
    when(mockConnect.get()).thenThrow(IllegalArgumentException.class);

    assertEquals("", queryEngine.executeQuery(QUERY, searchEngine));
  }

  /**
   * Test that if JSoup is unable to extract the result stats from the HTML document due to a
   * malformed selector, an empty String is returned.
   */
  @Test
  public void testExecuteQuery_noResultStats() {
    when(mockElements.first()).thenReturn(null);

    assertEquals("", queryEngine.executeQuery(QUERY, searchEngine));
  }

}
