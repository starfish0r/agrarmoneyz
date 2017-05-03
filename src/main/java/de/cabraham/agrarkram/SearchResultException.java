package de.cabraham.agrarkram;

public class SearchResultException extends Exception {
  private static final long serialVersionUID = 1L;

  private final SearchParam m_sp;

  public SearchResultException(SearchParam sp, String text) {
    super(text);
    m_sp = sp;
  }
  
  public String toString(){
    return super.toString() +" "+ m_sp.toString();
  }


}
