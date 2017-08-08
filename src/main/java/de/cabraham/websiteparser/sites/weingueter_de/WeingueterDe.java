package de.cabraham.websiteparser.sites.weingueter_de;

import java.util.LinkedList;
import java.util.List;

import de.cabraham.websiteparser.AbstractParsableSite;
import de.cabraham.websiteparser.AbstractSiteSearchParam;
import de.cabraham.websiteparser.PageResult;
import de.cabraham.websiteparser.SearchResultException;

public class WeingueterDe extends AbstractParsableSite<WeinGueterSearchParam, WeinGueterResult> {

  @Override
  public void openSearchDialog() {
    getDriver().get("https://www.weingueter.de/weingueter/");
  }
  
  @Override
  public WeinGueterSearchParam getSearchParams() {
    //none needed
    return null;
  }

  @Override
  public void performSearch(AbstractSiteSearchParam sp) throws SearchResultException {
    //no need, just the search page already shows all the results (but paginated)
  }

  @Override
  public List<WeinGueterResult> processResultList() {
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

}
