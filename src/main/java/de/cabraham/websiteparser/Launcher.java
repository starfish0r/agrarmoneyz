package de.cabraham.websiteparser;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import de.cabraham.websiteparser.sites.weingueter_de.WeinGueterResult;
import de.cabraham.websiteparser.sites.weingueter_de.WeinGueterResult.Tuple;
import de.cabraham.websiteparser.sites.weingueter_de.WeingueterDe;

public class Launcher {
  
  private final WebDriver m_driver;
  
  StringBuilder m_sb = new StringBuilder();

  private Map<String, List<WeinGueterResult>> m_plzMap = new HashMap<>();
  
  Launcher(){
    m_driver = new ChromeDriver();
  }

  public static void main(String[] args) {
    new Launcher().startTheThing();
  }
  
  private void startTheThing() {
    
    ParsableSite<? extends AbstractSiteSearchParam, ? extends AbstractSiteResult> a = new WeingueterDe();
    
    a.openSearchDialog();
    
    AbstractSiteSearchParam sp = a.getSearchParams();
    
    
    try {
      a.performSearch(sp);
    } catch (SearchResultException e) {
      Log.log("No search results: "+e.getMessage());
      return;
    }
    
    a.processResultList();

    
    //have to do this afterwards since the dictionary might change on the very last entry
    /*writeCSVHeader();
    for(Map.Entry<String, List<WeinGueterResult>>e:m_plzMap.entrySet()){
      writeCSV(e.getKey(), e.getValue());
    }
    try (FileWriter fw = new FileWriter("out.csv")) {
      fw.write(m_sb.toString());
      fw.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }*/
  }

  private void writeCSVHeader() {
    m_sb.append("PLZ;Name;Gesamt;");
    for(Integer i=0;i<Dict.nextKey;i++){
      m_sb.append(Dict.dict.get(i)).append(';');
    }
    m_sb.append("\r\n");
  }

  private void writeCSV(String plz, List<WeinGueterResult> plzResults) {
    for(WeinGueterResult dr:plzResults){
      m_sb.append(plz).append(';').append(dr.m_name==null?"":dr.m_name.replace("\"", "")).append(';').append(dr.total).append(';');
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

    
  
  

}
