package driver;

import java.util.HashMap;

public class ErrorHandler {
  
  HashMap<String, String> errorHash = new HashMap<String, String>();
  
  public ErrorHandler(String error_code) {
    errorHash.put("Invalid Command", "Error: Invalid Command");
    errorHash.put("Invalid Argument", "Error: Invalid Argument");
    errorHash.put("Invalid File", "Error: Invalid File");
    errorHash.put("Invalid Directory", "Error: Invalid Directory");
    errorHash.put("File Not Found", "Error: File Not Found");
    errorHash.put("Directory Not Found", "Error: Directory Not Found");
    errorHash.put("Same Directory", "Error: Same Directory with that name already exists!");
    
    if (errorHash.containsKey(error_code)){
    	System.out.println(errorHash.get(error_code));
    }
  }
  

}
