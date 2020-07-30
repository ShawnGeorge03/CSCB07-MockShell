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

import java.util.Arrays;

import data.FileSystemI;

/**
 * Class Echo is responsible for printing to the shell and redirecting the input
 * to perform the appropriate task
 */
public class Echo implements CommandI {

  /** Declare instance variable of String to hold the output that will be returned */
  String output = "";
  String properArgument = "";
  private RedirectionManager redirect;
  private ErrorHandler errorManager;

  public Echo(){
    this.errorManager = new ErrorHandler();
    this.redirect = new RedirectionManager();
  }

  /**
   * Main run method that checks if the user had inputted any arguments to the command. It splices the
   * input so that it can send parsed data to the appropriate implementation of echo. Calls the
   * execute method that will perform the required task for the appropriate scenario. Returns a String
   * that will either contain an error message if there was an error or return null
   * 
   * @param args  the String array of arguments provided by user (split from a whitespace)
   * @return String will either be null if there were no errors or an appropriate error message
   */
  public String run(FileSystemI filesys, String[] args, String fullInput, boolean val) {
    String[] arguments =  redirect.setParams(filesys, fullInput);
    
    if(arguments != null)
      output = redirect.outputResult(filesys, runEcho(arguments));    
    else{
      if (Arrays.asList(args).contains(">")) {
        output = redirect.setFileName(args, ">");
      } else {
        output = redirect.setFileName(args, ">>");
      }
    }

    return output;
  }

  private String runEcho(String[] args){
    // If no arguments were inputted
    if(args.length == 0) {
      return errorManager.getError("No parameters provided", "");
    }
    properArgument = "";
    for(int i = 0; i < args.length; i++){
      properArgument += args[i] + " ";
    }
    properArgument = properArgument.trim();
    if(hasQuotations(properArgument)) {
      output = properArgument.substring(1, properArgument.length()-1);
    } else
      // Missing quotations in user input
      output = errorManager.getError("Missing Quotes", args[0]);
    return output;
  }

  private boolean hasQuotations(String fullInput) {
      return fullInput.startsWith("\"") && fullInput.endsWith("\"");
  }
}
