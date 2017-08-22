package uk.amccabe.searchfight.search;

import uk.amccabe.searchfight.engine.SearchEngine;

/**
 * Interface for querying search engines. If any new search engine is introduced that is
 * incompatible with the current configuration-based implementation (e.g if an API call be
 * introduced), a new class should be created implementing this interface.
 * 
 * @author alan.mccabe92@gmail.com
 *
 */
public interface QuerySearchEngine {

  public String executeQuery(String queryString, SearchEngine engine);

}
