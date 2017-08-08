package de.cabraham.websiteparser;

import org.openqa.selenium.WebDriver;

public abstract class AbstractParsableSite<SPT extends AbstractSiteSearchParam, RT extends AbstractSiteResult> implements ParsableSite<SPT,RT> {
  
  private WebDriver driver = null;

  @Override
  public void setDriver(WebDriver webdriver) {
    driver = webdriver;
  }
  
  protected final WebDriver getDriver() {
    return driver;
  }

}
