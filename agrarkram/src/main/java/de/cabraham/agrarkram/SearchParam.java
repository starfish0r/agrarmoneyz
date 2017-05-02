package de.cabraham.agrarkram;

public class SearchParam {
  
  String m_plz;
  String m_amount;

  public SearchParam withPlz(String plz) {
    m_plz = plz;
    return this;
  }
  
  public SearchParam withAmount(String amount) {
    m_amount = amount;
    return this;
  }

  @Override
  public String toString() {
    return "(SearchParam plz=" + m_plz + ", amount="+m_amount+")";
  }

  

}
