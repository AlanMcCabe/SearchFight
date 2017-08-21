package uk.amccabe.searchfight;

import java.util.Arrays;
import java.util.List;

import uk.amccabe.searchfight.compare.QueryComparator;
import uk.amccabe.searchfight.engine.EngineLoader;
import uk.amccabe.searchfight.engine.SearchEngine;

/**
 * Main runnable class for the SearchFight application.
 * 
 * @author alan.mccabe92@gmail.com
 *
 */
public class Main {

  public static void main(String[] args) {
    EngineLoader engineLoader = new EngineLoader();
    List<SearchEngine> allEngines = engineLoader.getSupportedEngines();

    List<String> allQueries = Arrays.asList(args);

    if (!allQueries.isEmpty()) {
      QueryComparator comparator = new QueryComparator();
      comparator.executeAndCompare(allQueries, allEngines);
    }
  }

}
