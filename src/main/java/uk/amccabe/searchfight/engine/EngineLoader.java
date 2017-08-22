package uk.amccabe.searchfight.engine;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Load a List of specified SearchEngines from the @searchEngines.json configuration file.
 * 
 * @author alan.mccabe92@gmail.com
 *
 */
public class EngineLoader {

  private static final Logger logger = Logger.getLogger(EngineLoader.class);

  private ObjectMapper mapper;

  public EngineLoader() {
    mapper = new ObjectMapper();
  }

  /**
   * Load the configuration file and parse it into a list of SearchEngine objects using Jackson.
   * Exceptions should be caught and logged, and an empty list returned.
   * 
   * @return List of parsed SearchEngines, or an empty List if an exception occurs
   */
  public List<SearchEngine> getSupportedEngines(String fileName) {
    logger.debug(String.format("Attempting to load json file: %s", fileName));
    if (fileName != null && !fileName.isEmpty()) {
      TypeReference<List<SearchEngine>> listType = new TypeReference<List<SearchEngine>>() {};
      InputStream is = TypeReference.class.getResourceAsStream(fileName);

      try {
        return mapper.readValue(is, listType);
      } catch (IOException e) {
        logger.error("Exception occured while attempting to load search engine properties.");
        logger.error(e);
      }
    } else {
      logger.error("Missing configuration filename.");
    }

    return Collections.emptyList();
  }

}
