package uk.amccabe.searchfight;

import org.apache.log4j.Logger;

import uk.amccabe.searchfight.search.QueryEngine;
import uk.amccabe.searchfight.search.bing.QueryBing;
import uk.amccabe.searchfight.search.google.QueryGoogle;
import uk.amccabe.searchfight.search.yahoo.QueryYahoo;

/**
 * Main runnable class for the SearchFight application.
 * 
 * @author alan.mccabe92@gmail.com
 *
 */
public class Main {

  private static final Logger logger = Logger.getLogger(Main.class);

  public static void main(String[] args) {
    QueryEngine google = new QueryGoogle();
    QueryEngine bing = new QueryBing();
    QueryEngine yahoo = new QueryYahoo();
    
    for (int i = 0; i < args.length; i++) {      
      long googleResults = google.query(args[i]);
      long bingResults = bing.query(args[i]);
      long yahooResults = yahoo.query(args[i]);

      logger.debug("Got " + googleResults + " Google results.");
      logger.debug("Got " + bingResults + " Bing results.");
      logger.debug("Got " + yahooResults + " Yahoo results.");
    }

  }

}
