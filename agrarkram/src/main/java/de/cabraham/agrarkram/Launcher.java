package de.cabraham.agrarkram;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Launcher {
  
  private final WebDriver m_driver;
  
  Launcher(){
    m_driver = new ChromeDriver();
  }

  public static void main(String[] args) {
    new Launcher().startTheThing();
  }

  private void startTheThing() {
    List<String> lstPlz = loadPLZs();
    
    for(String plz:lstPlz) {
      SearchParam sp = new SearchParam().withPlz(plz).withAmount("500000");
      searchAndProcess(sp);
    }
  }

  private void searchAndProcess(SearchParam sp) {
    openSearchDialog();
    try {
      performSearch(sp);
    } catch (SearchResultException e) {
      e.printStackTrace();
      return;
    }
    SearchResultsPage.processSearchResultsPage(m_driver);
  }

  



  private void performSearch(SearchParam sp) throws SearchResultException {
    if(sp.m_plz != null){
      WebElement elInputPlz = m_driver.findElement(By.xpath("//input[@name='plz']"));
      elInputPlz.sendKeys(sp.m_plz);
    }
    if(sp.m_amount != null){
      WebElement elInputAmount = m_driver.findElement(By.xpath("//input[@name='betrag']"));
      elInputAmount.sendKeys(sp.m_amount);
      WebElement elDropComparison = m_driver.findElement(By.xpath("//select[@name='operator']"));
      Select sel = new Select(elDropComparison);
      sel.selectByValue("gt");
    }
    
    WebElement elSubmit = m_driver.findElement(By.xpath("//input[@type='submit']"));
    elSubmit.submit();
    
    try {
      final WebElement elError = m_driver.findElement(By.xpath("//span[@id='error']"));
      throw new SearchResultException(sp, elError.getText());
    } catch (NoSuchElementException e) {
      System.out.println("regular result page");
    }
  }

  private void openSearchDialog() {
    m_driver.get("https://www.agrar-fischerei-zahlungen.de/Suche");
  }

  private List<String> loadPLZs() {
    return Arrays.asList("14827");
  }
  
  

}
