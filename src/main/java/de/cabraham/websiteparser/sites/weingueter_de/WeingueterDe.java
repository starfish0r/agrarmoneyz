package de.cabraham.websiteparser.sites.weingueter_de;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;

import de.cabraham.websiteparser.AbstractParsableSite;
import de.cabraham.websiteparser.AbstractSiteSearchParam;
import de.cabraham.websiteparser.Log;
import de.cabraham.websiteparser.PageResult;
import de.cabraham.websiteparser.SearchResultException;

public class WeingueterDe extends AbstractParsableSite<WeinGueterSearchParam, WeinGueterResult> {

  @Override
  public void openSearchDialog() {
    getDriver().get("https://www.weingueter.de/weingueter/");
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {}
    getDriver().findElement(By.xpath("//a[@data-cc-event='click:dismiss']")).click();
  }
  
  @Override
  public WeinGueterSearchParam getSearchParams() {
    //none needed, main search page shows all results
    return null;
  }

  @Override
  public void performSearch(AbstractSiteSearchParam sp) throws SearchResultException {
    //no need, just the search page already shows all the results (but paginated)
  }

  @Override
  public List<WeinGueterResult> processSearchResultList() {
    PageResult<WeinGueterResult> pageResults;
    List<WeinGueterResult> lstResults = new LinkedList<>();
    while(true){
      pageResults = WeinGueterSearchResultListPage.processSearchResultsPage(getDriver());
      lstResults.addAll(pageResults.lstResult);
      if(pageResults.bButWaitTheresMore) {
        WeinGueterSearchResultListPage.nextPage(getDriver());
      } else {
        break; //all pages done
      }
    }
    return lstResults;
  }

  @Override
  public void doTheReport(List<WeinGueterResult> resultList) {
    Log.log("writing report for "+resultList.size()+" rows");
    
    StringBuilder sb = new StringBuilder();
    sb.append("Region;Name;Anschrift;Email;Ansprechpartner;Url;Telefon\r\n");
    
    for(WeinGueterResult r:resultList) {
      sb.append(String.format("%s;%s;%s;%s;%s;%s;%s\r\n", r.region, r.name, r.anschrift, r.email, r.ansprechpartner, r.url, r.telefon));
    }
    
    try (OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream("out.csv"), StandardCharsets.ISO_8859_1)) {
      fw.write(sb.toString());
      fw.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }

}
