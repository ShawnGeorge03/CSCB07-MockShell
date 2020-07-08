package commands;

import java.util.HashMap;

/**
 * Class ErrorHandler contains all possible errors 
 */
public class ErrorHandler {
  
  /**
   * Declare instance variable of HashMap to contain all the error names and error messages
   */
  HashMap<String, String> errorMap;
  
  /**
   * Constructor for ErrorHandler to initialize instance variables and populate HashMap
   */
  public ErrorHandler() {
    this.errorMap = new HashMap<String, String>();
    initializeErrorMap();
  }
  
  /**
   * Populates the HashMap with error names and error messages
   */
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
  
  /**
   * Returns error messages for requested error
   * 
   * @param errorCode  the name of the error
   * @param parameter  the parameter that the user provided
   * @return the error message
   */
  public String getError(String errorCode, String parameter) {
    if (errorMap.containsKey(errorCode))
      return errorMap.get(errorCode) + " : " + parameter;
    return null;
 }
}
