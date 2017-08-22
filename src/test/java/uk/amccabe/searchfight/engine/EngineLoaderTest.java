package uk.amccabe.searchfight.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

/**
 * Unit tests for the EngineLoader class.
 * 
 * @author alan.mccabe92@gmail.com
 *
 */
public class EngineLoaderTest {

  @InjectMocks
  private EngineLoader loader;

  @Before
  public void setUp() {
    loader = new EngineLoader();
  }

  /**
   * Test that a valid JSON configuration file is loaded and parsed into SearchEngine objects.
   */
  @Test
  public void testGetSupportedEngines() {
    List<SearchEngine> loadedEngines = loader.getSupportedEngines("/searchEngines.json");
    assertEquals(2, loadedEngines.size());
    
    SearchEngine engine1 = loadedEngines.get(0);    
    assertEquals("testName1", engine1.getName());
    assertEquals("testUrl1", engine1.getSearchUrl());
    assertEquals("testSelector1", engine1.getSelector());
    
    SearchEngine engine2 = loadedEngines.get(1);
    assertEquals("testName2", engine2.getName());
    assertEquals("testUrl2", engine2.getSearchUrl());
    assertEquals("testSelector2", engine2.getSelector());
  }

  /**
   * Test that an empty JSON configuration file results in an empty SearchEngine list.
   */
  @Test
  public void testGetSupportedEngines_emptyJson() {
    assertTrue(loader.getSupportedEngines("/searchEngines_empty.json").isEmpty());
  }
  
  /**
   * Test that an invalid JSON configuration file results in an empty SearchEngine list.
   */
  @Test
  public void testGetSupportedEngines_invalidJson() {
    assertTrue(loader.getSupportedEngines("/searchEngines_invalid.json").isEmpty());
  }
  
  /**
   * Test that a missing JSON configuration file results in an empty SearchEngine list.
   */
  @Test
  public void testGetSupportedEngines_noSuchFile() {
    assertTrue(loader.getSupportedEngines("non-existent").isEmpty());
  }
  
  /**
   * Test that calling the method with an empty String results in an empty SearchEngine list.
   */
  @Test
  public void testGetSupportedEngines_emptyArgument() {
    assertTrue(loader.getSupportedEngines("").isEmpty());
  }

  /**
   * Test that calling the method with a null argument results in an empty SearchEngine list.
   */
  @Test
  public void testGetSupportedEngines_nullArgument() {
    assertTrue(loader.getSupportedEngines(null).isEmpty());
  }

}
