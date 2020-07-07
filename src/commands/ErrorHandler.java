package commands;

import java.util.HashMap;

public class ErrorHandler {
  
  HashMap<String, String> errorMap;
  
  public ErrorHandler() {
    this.errorMap = new HashMap<String, String>();
    initializeErrorMap();
  }
  
  public void initializeErrorMap(){
    errorMap.put("Invalid Command", 
        "Error: Invalid Command");
    errorMap.put("Invalid Argument", 
        "Error: Invalid Argument");
    errorMap.put("Invalid File", 
        "Error: Invalid File");
    errorMap.put("Invalid Directory", 
        "Error: Invalid Directory");
    errorMap.put("File Not Found", 
        "Error: File Not Found");
    errorMap.put("Directory Not Found", 
        "Error: Directory Not Found");
    errorMap.put("Same Directory", 
        "Error: Same Directory with that name already exists!");
    errorMap.put("Missing Quotes", 
        "Error : Missing Quotes");
    errorMap.put("Malformed Input", 
        "Error : Malformed Input");
    errorMap.put("Mulptile parameters provided", 
        "Error : Multiple Parameters have been provided");
    errorMap.put("No parameters provided",
        "Error : No parameters provided");
  }
  
  public String getError(String errorCode, String parameter) {
    if (errorMap.containsKey(errorCode))
      return errorMap.get(errorCode) + " : " + parameter;
    return null;
 }
}
