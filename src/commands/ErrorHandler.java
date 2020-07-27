// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: patelt26
// UT Student #: 1005904103
// Author: Shawn Santhoshgeorge
//
// Student2:
// UTORID user_name: shaiskan
// UT Student #: 1006243940
// Author: Keshavaa Shaiskandan
//
// Student3:
// UTORID user_name: patelt26
// UT Student #: 1005904103
// Author: Tirth Patel
//
// Student4:
// UTORID user_name: pate1101
// UT Student #: 1006315765
// Author: Abhay Patel
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
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
    // Creates a HashMap Object called commandMap
    this.errorMap = new HashMap<String, String>();
    // Initializes the HashMap with the keys and values
    initializeErrorMap();
  }

  private void initializeErrorMap() {
    // Adds the respective error key and adds its respective erro message to the commandMap
    errorMap.put("Invalid Command", "Error: Invalid Command");
    errorMap.put("Invalid Argument", "Error: Invalid Argument");
    errorMap.put("Invalid File", "Error: Invalid File");
    errorMap.put("Invalid Directory", "Error: Invalid Directory");
    errorMap.put("File Not Found", "Error: File Not Found");
    errorMap.put("Directory Not Found", "Error: Directory Not Found");
    errorMap.put("Same Directory",
        "Error: Same Directory with that name already exists!");
    errorMap.put("Missing Quotes", "Error : Missing Quotes");
    errorMap.put("Malformed Input", "Error : Malformed Input");
    errorMap.put("Mulptile parameters provided",
        "Error : Multiple Parameters have been provided");
    errorMap.put("No parameters provided", "Error : No parameters provided");
    errorMap.put("Redirection Not allowed", "Error : Redirection Error");

  }

  /**
   * Returns error messages for requested error
   * 
   * @param errorCode  the name of the error
   * @param text  explanation for the error
   * @return the error message
   */
  public String getError(String errorCode, String text) {
    if (errorMap.containsKey(errorCode))
      return errorMap.get(errorCode) + " : " + text;
    return null;
  }
}
