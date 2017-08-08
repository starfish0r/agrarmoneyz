package de.cabraham.websiteparser.sites.weingueter_de;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


import de.cabraham.websiteparser.Log;
import de.cabraham.websiteparser.PageResult;

public class WeinGueterSearchResultListPage {
  
  private static final String XPATH_RESULT_TABLE = "//div[@id='content']//div[@class='bigbox']//div[@class='middle']//div[@class='inner']";
  
  static PageResult<WeinGueterResult> processSearchResultsPage(WebDriver m_driver) {
    PageResult<WeinGueterResult> pageResult = new PageResult<WeinGueterResult>();
    
    /*//set max number of rows per page to avoid page change issues
    Select selectLimit = new Select(m_driver.findElement(By.xpath("//select[@id='limit']")));
    selectLimit.selectByValue("50");*/
    
    final int nCurPage = Integer.valueOf(m_driver.findElement(By.xpath(XPATH_RESULT_TABLE + "//table[@class='table_blank']//td//b[2]")).getText());
    final String linkToLast = m_driver.findElement(By.xpath(XPATH_RESULT_TABLE + "//table[@class='table_blank']//td//a[last()]")).getAttribute("href");
    
    final Matcher m = Pattern.compile("page=(\\d+)\\.html").matcher(linkToLast);
    m.find();
    final int nTotalPages = Integer.valueOf(m.group(1));
    
    
    
    final List<WebElement> links = m_driver.findElements(By.xpath(XPATH_RESULT_TABLE + "/table[@class='table']//tr//b/a"));
    Actions actions = new Actions(m_driver);
    int i = 0;
    for(WebElement link:links) {
      Log.debug("Entry "+(++i)+" of "+links.size()+", page "+nCurPage+"/"+nTotalPages);
      actions.moveToElement(link).perform();
      actions.click(link).perform();
      /*JavaScriptExecutor ex = (JavaScriptExecutor)m_driver;
      ex.ExecuteScript("arguments[0].click();", link);*/
      final WeinGueterResult singleResult = WeinGueterSingleResultPage.parseSingleResultPage(m_driver);
      pageResult.lstResult.add(singleResult);
      m_driver.navigate().back();
    }
    pageResult.bButWaitTheresMore = nCurPage<nTotalPages;
    
    
    
    /*final String strSeiteVonX = m_driver.findElement(By.xpath("//*[input[@class='listNavTxtPage']]")).getText();
    final Matcher m = Pattern.compile("Seite\\s+von\\s+(\\d+)").matcher(strSeiteVonX);
    m.find();
    final int nTotalPages = Integer.valueOf(m.group(1));*/
    
    //not all rows lead to a detail page. only the buttons do
    /*List<WebElement> lstBegButtons = m_driver.findElements(By.xpath("//button[@class='linkBeg']"));
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
      final WeinGueterResult singleResult = WeinGueterSingleResultPage.parseSingleResultPage(m_driver);
      pageResult.lstResult.add(singleResult);
      m_driver.navigate().back();
      
      if(n==nCount-1) {//end reached
        pageResult.bButWaitTheresMore = nCurPage<nTotalPages;
        return pageResult;
      }
      n++;
    }*/
    return pageResult;
  }

  public static void nextPage(WebDriver m_driver) {
    m_driver.findElement(By.xpath("//input[@class='listNavSubNext']")).click();
  }

}
