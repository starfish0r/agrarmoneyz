package de.cabraham.websiteparser;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import de.cabraham.websiteparser.sites.weingueter_de.WeinGueterResult;
import de.cabraham.websiteparser.sites.weingueter_de.WeinGueterSearchParam;
import de.cabraham.websiteparser.sites.weingueter_de.WeingueterDe;

public class Launcher {
  
  private final WebDriver m_driver;
  
  StringBuilder m_sb = new StringBuilder();

  
  
  Launcher(){
    m_driver = new ChromeDriver();
    m_driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  public static void main(String[] args) {
    new Launcher().startTheThing();
  }
  
  private void startTheThing() {
    ParsableSite<WeinGueterSearchParam, WeinGueterResult> a = new WeingueterDe();
    
    a.setDriver(m_driver);
    a.openSearchDialog();
    AbstractSiteSearchParam sp = a.getSearchParams();
    try {
      a.performSearch(sp);
    } catch (SearchResultException e) {
      Log.log("No search results: "+e.getMessage());
      return;
    }
    final List<WeinGueterResult> resultList = a.processSearchResultList();
    a.doTheReport(resultList);
  }

}
