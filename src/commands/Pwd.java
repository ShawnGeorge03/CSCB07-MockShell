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
 * Class Pwd is responsible for providing the absolute path of the current
 * working directory
 */
public class Pwd extends DirectoryManager implements CommandI {

  /**
   * Declare instance of ErrorHandler to handle any errors that occur
   */
  ErrorHandler error;
  RedirectionManager redirect;
  String output;

  /**
   * Constructor for Pwd that initializes the ErrorHandler object
   */
  public Pwd() {
    // Initializes a ErrorHandler Object
    this.error = new ErrorHandler();
    this.redirect = new RedirectionManager();
  }

  /**
   * Run method first checks to see if the user has inputted any arguments, else it will return the
   * absolute path to the current working directory
   * 
   * @param args  the string array of arguments
   * @param fullInput  the full line of input that the user gives into JShell
   * @param val  stores a boolean value
   * @return String holding the absolute path to the current working directory, or an error message
   */
  public String run(FileSystemI filesys, String[] args, String fullInput, boolean val) {
    String[] arguments =  redirect.setParams(filesys, fullInput);

    if(arguments != null){
      output = redirect.outputResult(filesys, runPwd(filesys, arguments));
    }else{
      if (Arrays.asList(args).contains(">")) {
        output = redirect.setFileName(args, ">");
      } else {
        output = redirect.setFileName(args, ">>");
      }
    }
      
      
    return output;
  }

  private String runPwd(FileSystemI filesys, String[] args){
    // If the user provides any input for the following function
    if (args.length != 0) {
      // Returns an Invalid arguments error
      return error.getError("Invalid Argument",
          "pwd doesn't take any arguments");
    }
    String currentPath = filesys.getCurrentPath();
    if(currentPath.startsWith("//")) currentPath = currentPath.substring(1);
    // Returns the current working directory the user is in
    return currentPath;
  }

}
