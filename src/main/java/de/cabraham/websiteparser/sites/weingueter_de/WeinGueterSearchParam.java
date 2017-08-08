package de.cabraham.websiteparser.sites.weingueter_de;

import de.cabraham.websiteparser.AbstractSiteSearchParam;

public class WeinGueterSearchParam extends AbstractSiteSearchParam {
  
  String m_plz;
  String m_amount;

  public WeinGueterSearchParam withPlz(String plz) {
    m_plz = plz;
    return this;
  }
  
  public WeinGueterSearchParam withAmount(String amount) {
    m_amount = amount;
    return this;
  }

  @Override
  public String toString() {
    return "(SearchParam plz=" + m_plz + ", amount="+m_amount+")";
  }

  

}
