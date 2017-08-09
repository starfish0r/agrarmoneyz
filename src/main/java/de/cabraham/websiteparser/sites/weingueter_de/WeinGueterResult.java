package de.cabraham.websiteparser.sites.weingueter_de;

import de.cabraham.websiteparser.AbstractSiteResult;

public class WeinGueterResult extends AbstractSiteResult {
  public String region;
  public String name;
  public String anschrift;
  public String ansprechpartner;
  public String email;
  public String url;
  public String telefon;
  public void setEmail(String email) {
    this.email = email.trim();
  }
  public void setAnschrift(String xPathText) {
    this.anschrift = xPathText.replace("\n", ", ").trim();
  }
  public void setAnsprechpartner(String xPathText) {
    this.ansprechpartner = xPathText.replace("Ansprechperson: ", "").replace(';',',').trim();
  }
}
