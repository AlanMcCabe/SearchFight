package uk.amccabe.searchfight.engine;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EngineLoader {

  private static final Logger logger = Logger.getLogger(EngineLoader.class);

  public List<SearchEngine> getSupportedEngines() {
    ObjectMapper mapper = new ObjectMapper();
    TypeReference<List<SearchEngine>> listType = new TypeReference<List<SearchEngine>>() {};
    InputStream is = TypeReference.class.getResourceAsStream("/searchEngines.json");

    try {
      return mapper.readValue(is, listType);
    } catch (IOException e) {
      logger.error("Exception occured while attempting to load search engine properties.");
      logger.error(e);

      return Collections.emptyList();
    }
  }

}
