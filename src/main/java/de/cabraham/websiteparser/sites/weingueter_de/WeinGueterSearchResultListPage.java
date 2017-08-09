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
    
    final int nCurPage = Integer.valueOf(m_driver.findElement(By.xpath(XPATH_RESULT_TABLE + "//table[@class='table_blank']//td//b[2]")).getText());
    final String linkToLast = m_driver.findElement(By.xpath(XPATH_RESULT_TABLE + "//table[@class='table_blank']//td//a[last()]")).getAttribute("href");
    
    final Matcher m = Pattern.compile("page=(\\d+)\\.html").matcher(linkToLast);
    m.find();
    final int nTotalPages = Integer.valueOf(m.group(1));
    
    List<WebElement> rows = m_driver.findElements(By.xpath(XPATH_RESULT_TABLE + "/table[@class='table']//tr[td[1]//a]"));
    Actions actions = new Actions(m_driver);
    for(int i = 0;i<rows.size();i++) {
      if(i > 0) {
        //we use back() to get to the search page again, invalidating the previous document
        rows = m_driver.findElements(By.xpath(XPATH_RESULT_TABLE + "/table[@class='table']//tr[td[1]//a]"));
      }
      WebElement row = rows.get(i);
      WebElement link = row.findElement(By.xpath("td[1]//a"));
      Log.debug("Entry "+(i+1)+" of "+rows.size()+", page "+nCurPage+"/"+nTotalPages);
      //get the Region, which is in the 2nd table colum of the link above
      String region = row.findElement(By.xpath("td[2]//a")).getText();
      String name = link.getText();
      actions.moveToElement(link).perform();
      actions.click(link).perform();
      final WeinGueterResult singleResult = WeinGueterSingleResultPage.parseSingleResultPage(m_driver);
      singleResult.region = region;
      singleResult.name = name;
      pageResult.lstResult.add(singleResult);
      m_driver.navigate().back();
    }
    pageResult.bButWaitTheresMore = nCurPage<nTotalPages;

    return pageResult;
  }

  public static void nextPage(WebDriver driver) {
    Actions actions = new Actions(driver);
    final WebElement nextPageLink = driver.findElement(By.xpath(XPATH_RESULT_TABLE + "//a[img[@src='/img/arrow.gif']]"));
    actions.moveToElement(nextPageLink).click().perform();
  }

}
