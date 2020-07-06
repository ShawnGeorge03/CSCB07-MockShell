package commands;

import java.util.HashMap;

public class ErrorHandler {
  
  HashMap<String, String> errorMap;
  
  public ErrorHandler() {
    this.errorMap = new HashMap<String, String>();
    initializeErrorMap();
  }
  
  public void initializeErrorMap(){
    errorMap.put("Invalid Command", "Error: Invalid Command");
    errorMap.put("Invalid Argument", "Error: Invalid Argument");
    errorMap.put("Invalid File", "Error: Invalid File");
    errorMap.put("Invalid Directory", "Error: Invalid Directory");
    errorMap.put("File Not Found", "Error: File Not Found");
    errorMap.put("Directory Not Found", "Error: Directory Not Found");
    errorMap.put("Same Directory", "Error: Same Directory with that name already exists!");
  }
  
  public void getError(String errorCode) {
    if (errorMap.containsKey(errorCode)){
      System.out.println(errorMap.get(errorCode));
    }
    
    
  }

}
