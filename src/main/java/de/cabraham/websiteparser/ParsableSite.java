package de.cabraham.websiteparser;

import java.util.List;

import org.openqa.selenium.WebDriver;

public interface ParsableSite<SPT extends AbstractSiteSearchParam, RT extends AbstractSiteResult> {
  
  void setDriver(final WebDriver webdriver);
  
  void openSearchDialog();
  
  void performSearch(AbstractSiteSearchParam sp) throws SearchResultException;
  
  List<RT> processResultList();

  SPT getSearchParams();

}
