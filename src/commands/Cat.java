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
import data.Node;

/**
 * Class Cat views the contents of requested file
 */
public class Cat implements CommandI {

  /**
   * Declare instance variable of String to contain the output that Cat may return
   */
  private String output;

  private ErrorHandler errorManager;

  private RedirectionManager redirect;

  /**
   * Constructor for Cat that initializes instance variables
   */
  public Cat() {
    // Initializing the String object output
    this.output = "";
    this.errorManager = new ErrorHandler();
    this.redirect = new RedirectionManager();
  }

  /**
   * Checks if arguments are valid, then sends it to readFile to read the file
   * 
   * @param args  the string array of given arguments
   * @param fullInput  the string that contains the raw input given to JShell
   * @param val  stores a boolean value
   * @return the contents of file
   */
  public String run(FileSystemI fs, String[] args, String fullInput, boolean val) {
    String[] arguments = redirect.setParams(fs, fullInput);
    if(arguments != null){
      if (arguments.length == 0) {
        // Returns an error of No parameters provided
        return errorManager.getError("No parameters provided", "");
      } else {
        // Initializing the String object output after each time the method is called
        output = "";
        // Calls the readFile function to return what is in the file
        readFile(arguments, fs);
      }
      if(!output.contains("Error")) output = redirect.outputResult(fs, output);
    }else{
      if (Arrays.asList(args).contains(">")) {
        output = redirect.setFileName(args, ">");
      } else {
        output = redirect.setFileName(args, ">>");
      }
    }
    // Returns the output for the arguments
    return output;
  }

  private void readFile(String[] filePaths, FileSystemI filesys) {
    // Declares and initialized a Node to null
    Node file = null;
    // Runs through all the filePaths and stores the output for each case
    for (int i = 0; i < filePaths.length; i++) {
      file = filesys.findFile(filePaths[i], false);
      // If the file does exist
      if (file != null) {
        // Collect and append the text to the String object output
        output += filesys.getContent(file);
        // If the file does not exist
      } else {
        // Collect and append the error of File Not Found
        output = errorManager.getError("File Not Found", filePaths[i]);
        return;
      }

      // If it is not one file or it is the last file in the filePaths
      if (!(filePaths.length == 1 || i == filePaths.length - 1))
        // Append multiple line breaks to the String object output
        output += "\n" + "\n" + "\n";
    }
  }
}
