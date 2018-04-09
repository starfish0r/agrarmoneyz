package de.cabraham.websiteparser.sites.golf;

import de.cabraham.websiteparser.AbstractSiteSearchParam;
import de.cabraham.websiteparser.Log;
import de.cabraham.websiteparser.ParsableSite;
import de.cabraham.websiteparser.SearchResultException;
import de.cabraham.websiteparser.sites.weingueter_de.WeinGueterResult;
import de.cabraham.websiteparser.sites.weingueter_de.WeinGueterSearchParam;
import de.cabraham.websiteparser.sites.weingueter_de.WeingueterDe;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.temporal.TemporalAmount;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Launcher {
  
  private final WebDriver m_driver;
  
  Launcher(){
    System.setProperty("webdriver.chrome.driver", "C:\\dev\\eclipse\\tools\\chromedriver_win32\\chromedriver.exe");
    m_driver = new ChromeDriver();
    m_driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  public static void main(String[] args) {
    new Launcher().startTheThing();
  }
  
  private void startTheThing() {
    getDriver().get("http://www.golfclubs-germany.de/suche");


    //WebElement bundeslandTbl = getDriver().findElement(By.xpath("//*[@id=\"content\"]/div/div[2]/div[2]/div"));
    List<WebElement> bundesland = getDriver().findElements(By.xpath("//a"));
    //System.out.println(bundesland.size());
    List<String> blaender = new LinkedList<>();
    for(WebElement e:bundesland) {
      String href = e.getAttribute("href");
      if(href != null && href.endsWith(".html")) {
        blaender.add(href);
      }
    }

    System.out.println(blaender);

    Map<String, List<GolfClub>> resultMap = new HashMap<>();
    int total = 0;
    for (String bland:blaender){
      List<GolfClub> resultList = new LinkedList<>();
      getDriver().get(bland);
      List<WebElement> links = getDriver().findElements(By.xpath("//a"));
      List<String> singles = new LinkedList<>();
      for(WebElement link:links) {
        String href = link.getAttribute("href");
        if(href != null && href.contains("single&id=")){
          singles.add(href);
        }
      }
      for(String single:singles){
        getDriver().get(single);
        parseGolfClub(resultList);

        total++;
      }

      resultMap.put(bland, resultList);
    }

    System.out.println(total);



    /*ParsableSite<WeinGueterSearchParam, WeinGueterResult> a = new WeingueterDe();
    
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
    a.doTheReport(resultList);*/
  }

  private void parseGolfClub(List<GolfClub> resultList) {
    GolfClub g = new GolfClub();



    resultList.add(g);
  }

  private WebDriver getDriver() {
    return m_driver;
  }

  private class GolfClub {
  }
}
