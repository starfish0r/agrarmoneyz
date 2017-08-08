package de.cabraham.websiteparser.sites.agrar;

import de.cabraham.websiteparser.AbstractSiteSearchParam;

public class AgrarSearchParam extends AbstractSiteSearchParam {
  
  String m_plz;
  String m_amount;

  public AgrarSearchParam withPlz(String plz) {
    m_plz = plz;
    return this;
  }
  
  public AgrarSearchParam withAmount(String amount) {
    m_amount = amount;
    return this;
  }

  @Override
  public String toString() {
    return "(SearchParam plz=" + m_plz + ", amount="+m_amount+")";
  }

  

}
