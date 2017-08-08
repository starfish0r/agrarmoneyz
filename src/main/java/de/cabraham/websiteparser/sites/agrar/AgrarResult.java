package de.cabraham.websiteparser.sites.agrar;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import de.cabraham.websiteparser.AbstractSiteResult;

public class AgrarResult extends AbstractSiteResult {

  BigDecimal total;
  public String m_name;
  List<Tuple<Integer, BigDecimal>> values = new LinkedList<>();

  public static class Tuple<X, Y> {
    public final X x;
    public final Y y;

    public Tuple(X x, Y y) {
      this.x = x;
      this.y = y;
    }
  }

}
