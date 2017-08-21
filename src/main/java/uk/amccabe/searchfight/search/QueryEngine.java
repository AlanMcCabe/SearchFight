package uk.amccabe.searchfight.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import uk.amccabe.searchfight.engines.Engine;

public abstract class QueryEngine {
  
  protected String engineUrl;
  protected String userAgent;
  protected String selectResultStats;
  
  private static final Logger logger = Logger.getLogger(QueryEngine.class);
  
  public Map<String, Long> executeAllQueries(List<String> allQueries) {
    long result;
    Map<String, Long> resultMap = new HashMap<>();
    
    for(String query : allQueries) {
      result = executeQuery(query);
      
      resultMap.put(query, result);
    }
    
    return resultMap;
  }
  
  protected long executeQuery(String queryString) {
    try {
      String url = engineUrl + queryString;

      Document document = Jsoup.connect(url)
          .userAgent("Mozilla/5.0 (Windows; U; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727)")
          .get();

      Element resultStats = document.select(selectResultStats).first();
      if (resultStats == null) {
        throw new RuntimeException("Unable to find results stats.");
      }

      return parseResultString(resultStats.text());

    } catch (Exception e) {
      logger.error(e);
    }

    return 0;
  }

  protected long parseResultString(String result) {
    logger.debug("Result : " + result);
    String sanitizedText = result.replaceAll("[^\\d]", "");
    
    return Long.parseLong(sanitizedText);
  }

  public abstract long query(String queryString);

}
