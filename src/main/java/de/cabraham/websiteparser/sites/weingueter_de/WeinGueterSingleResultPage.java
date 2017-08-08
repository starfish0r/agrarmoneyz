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
      dr.telefon = box.findElement(By.xpath("table//tr[td/text()='Tel.']/td[2]")).getText();
    } catch (NoSuchElementException e) {
      Log.log("nse", e);
    }
    return dr;
  }

  private static void sleep(int i) {
    try {
      Thread.sleep(i);
    } catch (InterruptedException e) { }
  }


}
