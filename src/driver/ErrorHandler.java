package driver;

import java.util.HashMap;

public class ErrorHandler {
  
  HashMap<String, String> errorHash = new HashMap<String, String>();
  
  public ErrorHandler(String error_code) {
    System.out.println(errorHash.get(error_code));
  }
  

}
