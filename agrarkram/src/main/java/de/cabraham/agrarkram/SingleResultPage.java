package de.cabraham.agrarkram;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SingleResultPage {
  static DetailedResult parseSingleResultPage(WebDriver driver) {
    DetailedResult dr = new DetailedResult();
    dr.m_name = driver.findElement(By.xpath("//div[@id='beguenstigter']/h2")).getText();

    final List<WebElement> lstEntries = driver.findElements(By.xpath("//div[@id='beguenstigter']/h3"));
    for (WebElement we : lstEntries) {
      Log.debug(we.getText());
      Log.debug(driver.findElement(By.xpath("//following-sibling::p/span[@class='betrag']")).getText());
    }

    final List<WebElement> amounts = driver.findElements(By.xpath("//span[@class='betrag']"));

    String strTotal = amounts.get(amounts.size() - 2).getText();
    Log.debug(strTotal);

    dr.total = BigDecimal.ZERO;
    return dr;
  }

  public static void main(String[] args) throws ParseException {
    

    final NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
    System.out.println(nf.parse("559.830,57"));
    
    /*DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    symbols.setDecimalSeparator(',');
    symbols.setGroupingSeparator('.');
    DecimalFormat format = new DecimalFormat("0#");
    format.setDecimalFormatSymbols(symbols);
    float f = format.parse("559.830,57").floatValue();
    System.out.println(f);*/
  }
}
