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
 * Class history keeps track of all inputs given to JShell
 */
public class History implements CommandI {

  /**
   * Declare instance variable of ErrorHandler to handle error messages
   */
  private ErrorHandler error;
  /**
   * Declare instance variable of String to contain the output that the command may return
   */
  String output;

  /**
   * Constructor for History that initializes instance variables
   */
  public History() {
    // Initializes a ErrorHandler Object
    this.error = new ErrorHandler();
    // Initializes a String object called output
    this.output = "";
  }

  /**
   * Parses input and then calls print method to print history
   * 
   * @param args  the string array that contains given arguments
   * @param fullInput  the string that contains the raw input given to JShell
   * @param val  stores a boolean value
   * @return the error message if there is any or the actual history
   */
  public String run(FileSystemI fs, String[] args, String fullInput, boolean val) {
    // If the user provides no arguments
    if (args.length == 0) {
      // Calls the following methods to print the entire history
      printLastXCommands(fs, fs.getCommandLog().size());
      // If the user provided 1 argument
    } else if (args.length == 1) {
      // Created and initialized an integer
      int number = 0;
      // Created and initialized a boolean
      boolean numeric = true;

      // Converts the given input from the user to an integer
      try {
        // Stores the converted value if it was possible
        number = Integer.valueOf(args[0]);
        // If the user provided something other than an Integer
      } catch (NumberFormatException e) {
        // Sets the boolean to false
        numeric = false;
      }

      // If the input is an number and non-negative and an integer
      if (numeric && number >= 0 && number % 1 == 0) {
        // Calls the following method to get the history to a certain number of
        // commands the user inputed
        printLastXCommands(fs, number);
      } else {
        // Sets an error of Invalid Argument to be returned to the user
        output = error.getError("Invalid Argument",
            args[0] + " is not either a number or positive or an integer");
      }
      // If the user provided multiple arguments
    } else if (args.length > 1) {

      // Collects the user input from the fullInput
      String parameter = Arrays.toString(args);
      parameter = parameter.substring(1, parameter.length() - 1).
          replace(",", "").trim();
      // Returns an error of Mulptile parameters provided
      output = error.getError("Multiple parameters provided",
          parameter + " , either one or no input");      
    }

    // Returns the valid output for the user input
    return output.trim();
  }

  private void printLastXCommands(FileSystemI fs, int x) {
    // Reads and stores the commands from the CommandLod in FileSystem
    for (int i = fs.getCommandLog().size() - x; i < fs.getCommandLog().size() ; i++) {
      // If the counter is a negative number
      if (i < 0) {
        // Skips from reading the commandLog
        continue;
      }
      // Collects the the commands from the CommandLog
      output += (i + 1) + ". " + fs.getCommandLog().get(i) + "\n";
    }
  }
}
