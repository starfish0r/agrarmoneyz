package de.cabraham.websiteparser;

import org.apache.commons.collections4.bidimap.TreeBidiMap;

public class Dict {
  
  public static TreeBidiMap<Integer, String> dict = new TreeBidiMap<Integer, String>();
  static Integer nextKey = 0;

  public static Integer introduceValue(String strCategory) {
    Integer usedKey = nextKey++;
    dict.put(usedKey, strCategory);
    return usedKey;
  }

}
