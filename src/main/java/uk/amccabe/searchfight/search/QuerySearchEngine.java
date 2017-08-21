package uk.amccabe.searchfight.search;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import uk.amccabe.searchfight.engine.SearchEngine;

public class QuerySearchEngine {

  private static final Logger logger = Logger.getLogger(QuerySearchEngine.class);

  // public Map<String, Long> executeAllQueries(List<String> allQueries) {
  // long result;
  // Map<String, Long> resultMap = new HashMap<>();
  //
  // for(String query : allQueries) {
  // result = executeQuery(query);
  //
  // resultMap.put(query, result);
  // }
  //
  // return resultMap;
  // }

  public String executeQuery(String queryString, SearchEngine engine) {
    try {
      String url = engine.getSearchUrl() + queryString;

      Document document = Jsoup.connect(url)
          .userAgent("Mozilla/5.0 (Windows; U; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727)")
          .get();

      Element resultStats = document.select(engine.getSelector()).first();
      if (resultStats == null) {
        throw new RuntimeException("Unable to find results stats.");
      }

      return resultStats.text();

    } catch (Exception e) {
      logger.error(e);
    }

    return null;
  }

}
