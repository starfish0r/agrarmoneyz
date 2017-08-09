package de.cabraham.websiteparser;

import java.nio.charset.StandardCharsets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Util {

  public static String getXPathText(WebElement parent, String xpath) {
    try {
      String text = parent.findElement(By.xpath(xpath)).getText();
      return text;
    } catch (Exception e) {
      Log.log("moep: "+xpath, e);
    }
    return null;
  }

  
  public static String isoToUtf8(String text)  {
    if(text==null) {
      return null;
    }
    return new String(text.getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
  }
}
