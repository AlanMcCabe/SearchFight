package uk.amccabe.searchfight;

import java.util.Arrays;
import java.util.List;

import uk.amccabe.searchfight.compare.QueryComparator;
import uk.amccabe.searchfight.engine.EngineLoader;
import uk.amccabe.searchfight.engine.SearchEngine;

/**
 * Main runnable class for the SearchFight application. Simply delegates loading of SearchEngine
 * information and execution of queries to other classes.
 * 
 * engineLoader.getSupportedEngines() is passed a relative path to the @searchEngines.json file, however
 * this could be easily modified to use a command line argument.
 * 
 * @author alan.mccabe92@gmail.com
 *
 */
public class Main {

  public static void main(String[] args) {
    EngineLoader engineLoader = new EngineLoader();
    List<SearchEngine> allEngines = engineLoader.getSupportedEngines("/searchEngines.json");

    List<String> allQueries = Arrays.asList(args);

    if (!allQueries.isEmpty() && !allEngines.isEmpty()) {
      QueryComparator comparator = new QueryComparator();
      comparator.executeAndCompare(allQueries, allEngines);
    }
  }

}
