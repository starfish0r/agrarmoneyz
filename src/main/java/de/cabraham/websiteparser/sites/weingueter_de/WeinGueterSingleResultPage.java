package de.cabraham.websiteparser.sites.weingueter_de;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.cabraham.websiteparser.Log;

public class WeinGueterSingleResultPage {
  
  
  static WeinGueterResult parseSingleResultPage(WebDriver driver) {
    WeinGueterResult dr = new WeinGueterResult();
    sleep(100);
    try {
      WebElement box = driver.findElement(By.xpath("//div[span/.='Anschrift']"));
      dr.anschrift = box.findElement(By.xpath("b")).getText();
      dr.ansprechpartner = box.findElement(By.xpath("table//tr[1]")).getText();
      
      dr.tel = box.findElement(By.xpath("table//tr[td/text()='Tel.']/td[2]")).getText();
      System.out.println(dr.anschrift);
    } catch (NoSuchElementException e) {
      Log.log("nse", e);
    }

    /*final List<WebElement> lstEntries = driver.findElements(By.xpath("//div[@id='beguenstigter']/h3"));
    for (WebElement we : lstEntries) {
      String strCategory = we.getText().trim();
      String strRawAmount = we.findElement(By.xpath("following-sibling::p/span[@class='betrag']")).getText();
      BigDecimal bdAmount = parse(strRawAmount);
      dr.values.add(fillTuple(strCategory, bdAmount));
      //Log.debug(strCategory+"="+strRawAmount);
    }

    final List<WebElement> amounts = driver.findElements(By.xpath("//span[@class='betrag']"));

    String strRawTotal = amounts.get(amounts.size() - 2).getText();
    dr.total = parse(strRawTotal);*/
    return dr;
  }

  private static void sleep(int i) {
    try {
      Thread.sleep(i);
    } catch (InterruptedException e) { }
  }


}
