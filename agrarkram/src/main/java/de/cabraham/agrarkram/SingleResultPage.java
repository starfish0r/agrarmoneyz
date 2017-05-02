package de.cabraham.agrarkram;

import java.math.BigDecimal;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.cabraham.agrarkram.DetailedResult.Tuple;

public class SingleResultPage {
  static DetailedResult parseSingleResultPage(WebDriver driver) {
    DetailedResult dr = new DetailedResult();
    dr.m_name = driver.findElement(By.xpath("//div[@id='beguenstigter']/h2")).getText();

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
  
  
  public static void main(String[] args) {
    String d = "1.125.141,20 €";
    String d2 = "1,125,141.20 €";
    System.out.println(parse(d).equals(BigDecimal.valueOf(1125141.2)));
    System.out.println(parse(d2).equals(BigDecimal.valueOf(1125141.2)));
  }

}
