package de.cabraham.agrarkram;

public class Log {
  
  static boolean m_bDebug = true;
  
  static void debug(String str){
    if(m_bDebug){
      System.out.println(str);
    }
  }

  public static void log(String str) {
    System.out.println(str);
  }

}
