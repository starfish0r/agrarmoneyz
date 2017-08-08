package de.cabraham.websiteparser.sites.weingueter_de;

import java.math.BigDecimal;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.cabraham.websiteparser.Dict;
import de.cabraham.websiteparser.Log;
import de.cabraham.websiteparser.sites.weingueter_de.WeinGueterResult;
import de.cabraham.websiteparser.sites.weingueter_de.WeinGueterResult.Tuple;

public class WeinGueterSingleResultPage {
  static WeinGueterResult parseSingleResultPage(WebDriver driver) {
    WeinGueterResult dr = new WeinGueterResult();
    sleep(100);
    try {
      dr.m_name = driver.findElement(By.xpath("//div[@id='beguenstigter']/h2")).getText();
    } catch (NoSuchElementException e) {
      Log.log("beguenstigter not found");
    }

    final List<WebElement> lstEntries = driver.findElements(By.xpath("//div[@id='beguenstigter']/h3"));
    for (WebElement we : lstEntries) {
      String strCategory = we.getText().trim();
      String strRawAmount = we.findElement(By.xpath("following-sibling::p/span[@class='betrag']")).getText();
      BigDecimal bdAmount = parse(strRawAmount);
      dr.values.add(fillTuple(strCategory, bdAmount));
      //Log.debug(strCategory+"="+strRawAmount);
    }

    final List<WebElement> amounts = driver.findElements(By.xpath("//span[@class='betrag']"));

    String strRawTotal = amounts.get(amounts.size() - 2).getText();
    dr.total = parse(strRawTotal);
    return dr;
  }

  private static void sleep(int i) {
    try {
      Thread.sleep(i);
    } catch (InterruptedException e) { }
  }

  private static Tuple<Integer, BigDecimal> fillTuple(String strCategory, BigDecimal bdAmount) {
    Integer key;
    if(Dict.dict.containsValue(strCategory)){
      key = Dict.dict.getKey(strCategory);
    } else {
      key = Dict.introduceValue(strCategory);
    }
    return new Tuple<Integer, BigDecimal>(key, bdAmount);
  }

  public static BigDecimal parse(String raw) {
    raw = raw.replaceAll("[ €]", "").trim();
    char potentialDecimalPoint = raw.charAt(raw.length()-3);
    if(potentialDecimalPoint == '.'){
      raw = raw.replaceAll(",", "");
    } else if(potentialDecimalPoint== ','){
      raw = raw.replaceAll("\\.", "");
      raw = raw.replace(',', '.');
    }
    return BigDecimal.valueOf(Double.valueOf(raw));
  }
  

}
