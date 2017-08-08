package de.cabraham.websiteparser.sites.agrar;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import de.cabraham.websiteparser.Log;
import de.cabraham.websiteparser.PageResult;
import de.cabraham.websiteparser.sites.weingueter_de.WeinGueterResult;

public class SearchResultsPage {
  
  static PageResult<AgrarResult> processSearchResultsPage(WebDriver m_driver) {
    PageResult<AgrarResult> pageResult = new PageResult<AgrarResult>();
    
    //set max number of rows per page to avoid page change issues
    Select selectLimit = new Select(m_driver.findElement(By.xpath("//select[@id='limit']")));
    selectLimit.selectByValue("50");
    
    //find "page X of Y"
    final int nCurPage = Integer.valueOf(m_driver.findElement(By.xpath("//input[@class='listNavTxtPage']")).getAttribute("value"));
    final String strSeiteVonX = m_driver.findElement(By.xpath("//*[input[@class='listNavTxtPage']]")).getText();
    final Matcher m = Pattern.compile("Seite\\s+von\\s+(\\d+)").matcher(strSeiteVonX);
    m.find();
    final int nTotalPages = Integer.valueOf(m.group(1));
    
    //not all rows lead to a detail page. only the buttons do
    List<WebElement> lstBegButtons = m_driver.findElements(By.xpath("//button[@class='linkBeg']"));
    int nCount = lstBegButtons.size();
    
    int n = 0;
    while(n<nCount) {
      if(n>0){
        //after the first button we changed the current website so the ids of the old list are not valid anymore
        lstBegButtons = m_driver.findElements(By.xpath("//button[@class='linkBeg']"));
      }
      Log.debug("Entry "+(n+1)+" of "+nCount+", page "+nCurPage+"/"+nTotalPages);
      //click the entry, parse it, then go back to the search result page
      WebElement btn = lstBegButtons.get(n);
      btn.click();
      final AgrarResult singleResult = SingleResultPage.parseSingleResultPage(m_driver);
      pageResult.lstResult.add(singleResult);
      m_driver.navigate().back();
      
      if(n==nCount-1) {//end reached
        pageResult.bButWaitTheresMore = nCurPage<nTotalPages;
        return pageResult;
      }
      n++;
    }
    return pageResult;
  }

  public static void nextPage(WebDriver m_driver) {
    m_driver.findElement(By.xpath("//input[@class='listNavSubNext']")).click();
  }

}
