package de.cabraham.websiteparser.sites.weingueter_de;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.cabraham.websiteparser.Log;
import de.cabraham.websiteparser.Util;

public class WeinGueterSingleResultPage {
  
  
  static WeinGueterResult parseSingleResultPage(WebDriver driver) {
    WeinGueterResult dr = new WeinGueterResult();
    sleep(200);
    try {
      WebElement box = driver.findElement(By.xpath("//div[span/.='Anschrift']"));
      //dr.name = 
      dr.setEmail(Util.getXPathText(box, "table//tr[td/text()='e-mail']/td[2]"));
      dr.setAnschrift(Util.getXPathText(box, "b"));
      dr.setAnsprechpartner(Util.getXPathText(box, "table//tr[1]"));
      dr.telefon = Util.getXPathText(box, "table//tr[td/text()='Tel.']/td[2]");
      dr.url = Util.getXPathText(box, "table//tr[td/text()='Web']/td[2]");
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
