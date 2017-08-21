package uk.amccabe.searchfight.search.google;

import org.apache.log4j.Logger;

import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchException;
import com.google.appengine.api.search.SearchServiceFactory;

import uk.amccabe.searchfight.search.QueryEngine;

public class QueryGoogle extends QueryEngine {

  private static final Logger logger = Logger.getLogger(QueryGoogle.class);

  @Override
  public int executeQuery(String queryString) {
    int totalResults = 0;

    try {
      int resultsRetrieved = 0;
      int offset = 0;

      do {
        // @formatter:off
        QueryOptions options = QueryOptions.newBuilder()
            .setOffset(offset)
            .build();
        // @formatter:on

        Query query = Query.newBuilder().setOptions(options).build(queryString);
        IndexSpec indexSpec = IndexSpec.newBuilder().setName("name").build();
        Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);

        Results<ScoredDocument> results = index.search(query);
        resultsRetrieved = results.getNumberReturned();

        if (resultsRetrieved > 0) {
          offset += resultsRetrieved;
          totalResults += resultsRetrieved;
        }

      } while (resultsRetrieved > 0);
    } catch (SearchException e) {
      logger.error("Exception occured while trying to execute Google request.");
    }

    return totalResults;
  }



}
