package uk.amccabe.searchfight.engine;

/**
 * POJO representing a search engine containing all fields necessary to identify it, execute search
 * queries and extract the results.
 * 
 * @author alan.mccabe92@gmail.com
 *
 */
public class SearchEngine {

  private String name;
  private String searchUrl;
  private String selector;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSearchUrl() {
    return searchUrl;
  }

  public void setSearchUrl(String searchUrl) {
    this.searchUrl = searchUrl;
  }

  public String getSelector() {
    return selector;
  }

  public void setSelector(String selector) {
    this.selector = selector;
  }

}
