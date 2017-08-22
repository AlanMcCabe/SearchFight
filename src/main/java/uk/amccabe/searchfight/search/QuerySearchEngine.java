package uk.amccabe.searchfight.search;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import uk.amccabe.searchfight.engine.SearchEngine;

/**
 * Execute a query on a specific SearchEngine and return the result text.
 * 
 * The query is executed using Jsoup impersonating a web browser in order to have the full HTML
 * returned. Once this has been received we can extract the number of results from a known area in
 * the Document as specified in @searchEngines.json and return it as a String.
 * 
 * @author alan.mccabe92@gmail.com
 *
 */
public class QuerySearchEngine {

  private static final Logger logger = Logger.getLogger(QuerySearchEngine.class);

  /**
   * Execute a query on a specified search engine, extract the result and return it.
   * 
   * @param queryString String query to be executed
   * @param engine SearchEngine the query is to be executed on
   * @return String result as extracted from the HTML response
   */
  public String executeQuery(String queryString, SearchEngine engine) {
    try {
      String url = engine.getSearchUrl() + queryString;

      Document document = Jsoup.connect(url)
          .userAgent("Mozilla/5.0 (Windows; U; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727)")
          .get();

      Element resultStats = document.select(engine.getSelector()).first();
      if (resultStats == null) {
        throw new RuntimeException("Unable to find result stats from HTML response.");
      }

      return resultStats.text();

    } catch (Exception e) {
      logger.error(String.format(
          "Exception occured while trying to execute search query \"%s\" on search engine %s.",
          queryString, engine.getName()));
      logger.error(e);
      return "";
    }
  }

}
