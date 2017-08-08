package de.cabraham.websiteparser;

public class SearchResultException extends Exception {
  private static final long serialVersionUID = 1L;

  private final AbstractSiteSearchParam m_sp;

  public SearchResultException(AbstractSiteSearchParam sp, String text) {
    super(text);
    m_sp = sp;
  }
  
  public String toString(){
    return super.toString() +" "+ m_sp.toString();
  }


}
