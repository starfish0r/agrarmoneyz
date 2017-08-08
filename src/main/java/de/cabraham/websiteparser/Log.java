package de.cabraham.websiteparser;

public class Log {
  
  static boolean m_bDebug = true;
  
  public static void debug(String str){
    if(m_bDebug){
      System.out.println(str);
    }
  }

  public static void log(String str) {
    System.out.println(str);
  }

}
