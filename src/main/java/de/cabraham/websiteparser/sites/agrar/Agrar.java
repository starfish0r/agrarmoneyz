package de.cabraham.websiteparser.sites.agrar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import de.cabraham.websiteparser.AbstractParsableSite;
import de.cabraham.websiteparser.AbstractSiteSearchParam;
import de.cabraham.websiteparser.PageResult;
import de.cabraham.websiteparser.SearchResultException;

public class Agrar extends AbstractParsableSite<AgrarSearchParam, AgrarResult> {

  @Override
  public void openSearchDialog() {
    getDriver().get("die-agrar-url-wie-auch-immer-sie-war");
  }


  @Override
  public void performSearch(AbstractSiteSearchParam sp1) throws SearchResultException {
    AgrarSearchParam sp = (AgrarSearchParam) sp1;
    if(sp.m_plz != null){
      WebElement elInputPlz = getDriver().findElement(By.xpath("//input[@name='plz']"));
      elInputPlz.sendKeys(sp.m_plz);
    }
    if(sp.m_amount != null){
      WebElement elInputAmount = getDriver().findElement(By.xpath("//input[@name='betrag']"));
      elInputAmount.sendKeys(sp.m_amount);
      WebElement elDropComparison = getDriver().findElement(By.xpath("//select[@name='operator']"));
      Select sel = new Select(elDropComparison);
      sel.selectByValue("gt");
    }
    
    WebElement elSubmit = getDriver().findElement(By.xpath("//input[@type='submit']"));
    elSubmit.submit();
    
    try {
      final WebElement elError = getDriver().findElement(By.xpath("//span[@id='error']"));
      throw new SearchResultException(sp, elError.getText());
    } catch (NoSuchElementException e) {
      System.out.println("regular result page");
    }
    
  }


  @Override
  public List<AgrarResult> processSearchResultList() {
    PageResult<AgrarResult> pageResults;
    List<AgrarResult> lstResults = new LinkedList<>();
    while(true){
      pageResults = SearchResultsPage.processSearchResultsPage(getDriver());
      lstResults.addAll(pageResults.lstResult);
      if(pageResults.bButWaitTheresMore) {
        SearchResultsPage.nextPage(getDriver());
      } else {
        break; //all pages done
      }
    }
    return lstResults;
  }

  @Override
  public AgrarSearchParam getSearchParams() {
    // TODO Auto-generated method stub
    return null;
  }
  
  
  /*Set<String> lstPlz = loadPLZs();
  
  for(String plz:lstPlz) {
    Log.debug("starting plz "+plz);
    SearchParam sp = new SearchParam();
    List<DetailedResult> plzResults = searchAndProcess(sp);
    if(plzResults != null){
      m_plzMap.put(plz, plzResults);
    }
  }*/
  
  /*private Map<String, List<WeinGueterResult>> m_plzMap = new HashMap<>();*/
  
  
  static List<String> extractPlzs(Path p) {
    String content;
    try {
      content = Files.readAllLines(p).stream().collect(Collectors.joining());
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
    final Matcher matcher = Pattern.compile("\\d{5}").matcher(content);
    List<String> ret = new LinkedList<>();
    while(matcher.find()){
      ret.add(matcher.group());
    }
    return ret;
  }


  @Override
  public void doTheReport(List<AgrarResult> processSearchResultList) {
    // TODO Auto-generated method stub
    
  }


}
