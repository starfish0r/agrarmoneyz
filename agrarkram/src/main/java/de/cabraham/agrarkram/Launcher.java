package de.cabraham.agrarkram;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import de.cabraham.agrarkram.DetailedResult.Tuple;

public class Launcher {
  
  private final WebDriver m_driver;
  
  StringBuilder m_sb = new StringBuilder();

  private Map<String, List<DetailedResult>> m_plzMap = new HashMap<>();
  
  Launcher(){
    m_driver = new ChromeDriver();
  }

  public static void main(String[] args) {
    new Launcher().startTheThing();
  }
  
  private void startTheThing() {
    List<String> lstPlz = loadPLZs();
    
    for(String plz:lstPlz) {
      SearchParam sp = new SearchParam().withPlz(plz);//.withAmount("500000");
      List<DetailedResult> plzResults = searchAndProcess(sp);
      m_plzMap.put(plz, plzResults);
    }
    
    //have to do this afterwards since the dictionary might change on the very last entry
    writeCSVHeader();
    for(Map.Entry<String, List<DetailedResult>>e:m_plzMap.entrySet()){
      writeCSV(e.getKey(), e.getValue());
    }
    try (FileWriter fw = new FileWriter("out.csv")) {
      fw.write(m_sb.toString());
      fw.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void writeCSVHeader() {
    m_sb.append("PLZ;Name;Gesamt;");
    for(Integer i=0;i<Dict.nextKey;i++){
      m_sb.append(Dict.dict.get(i)).append(';');
    }
    m_sb.append("\r\n");
  }

  private void writeCSV(String plz, List<DetailedResult> plzResults) {
    for(DetailedResult dr:plzResults){
      m_sb.append(plz).append(';').append(dr.m_name).append(';').append(dr.total).append(';');
      for(Integer i=0;i<Dict.nextKey;i++){
        m_sb.append(getAmountIfKeyPresent(i, dr.values)).append(';');
      }
      m_sb.append("\r\n");
    }
  }

  private String getAmountIfKeyPresent(Integer i, List<Tuple<Integer, BigDecimal>> values) {
    for(Tuple<Integer, BigDecimal> t:values){
      if(i.equals(t.x)){
        return t.y.toString();
      }
    }
    return "";
  }

  private List<DetailedResult> searchAndProcess(SearchParam sp) {
    openSearchDialog();
    try {
      performSearch(sp);
    } catch (SearchResultException e) {
      e.printStackTrace();
      return null;
    }
    PageResult pageResults;
    List<DetailedResult> lstResults = new LinkedList<>();
    while(true){
      pageResults = SearchResultsPage.processSearchResultsPage(m_driver);
      lstResults.addAll(pageResults.lstResult);
      if(pageResults.bButWaitTheresMore) {
        SearchResultsPage.nextPage(m_driver);
      } else {
        break; //all pages done
      }
    }
    return lstResults;
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
    //https://www.suche-postleitzahl.org/sachsen-anhalt.7a
    return Arrays.asList("14827");
  }
  
  

}
