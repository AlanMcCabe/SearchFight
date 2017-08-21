package uk.amccabe.searchfight;

import org.apache.log4j.Logger;

import uk.amccabe.searchfight.search.QueryEngine;
import uk.amccabe.searchfight.search.google.QueryGoogle;

/**
 * Main runnable class for the SearchFight application.
 * 
 * @author alan.mccabe92@gmail.com
 *
 */
public class Main {

  private static final Logger logger = Logger.getLogger(Main.class);

  public static void main(String[] args) {
    for (int i = 0; i <= args.length; i++) {
      QueryEngine engine = new QueryGoogle();
      int results = engine.executeQuery(args[i]);

      logger.debug("Got " + results + " results.");
    }

  }

}
