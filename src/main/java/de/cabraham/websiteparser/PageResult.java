package de.cabraham.websiteparser;

import java.util.LinkedList;
import java.util.List;

public class PageResult<RT extends AbstractSiteResult> {
  
  public boolean bButWaitTheresMore = false;
  public List<RT> lstResult = new LinkedList<>();
  

}
