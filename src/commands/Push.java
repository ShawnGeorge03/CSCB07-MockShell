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

import commands.DirectoryManager;
import data.FileSystem;

/**
 * Class Push is responsible forSaves the current working directory to 
 * the directory stack and changes the current working directory to 
 * the one the user gave if it is valid
 */
public class Push extends DirectoryManager implements CommandI {

  /**
   * Declare an instance of ErrorHandler to handle any errors that occur
   */
  private ErrorHandler errorManager;

  /**
   * Constructor for Push that initializes the ErrorHandler object
   */
  public Push() {
    this.errorManager = new ErrorHandler();
  }

  /**
   * Saves the current working directory to the directory stack and changes the
   * current working directory to the one the user gave if it is valid
   * 
   * @param args  the string array of arguments
   * @param fullInput  the full line of input that the user gives into JShell
   * 
   * @return String  An error message if any error, else null
   */
  public String run(String[] args, String fullInput, boolean val) {
    Cd goBack = new Cd();
    String[] root = {FileSystem.getFileSys().getRoot().getName()};

    if (args.length != 1) {
      return errorManager.getError("Invalid Argument",
          Integer.toString(args.length) + " arguments, expecting 1 argument");
    }

    if (FileSystem.getStack().isEmpty()) {
      FileSystem.getStack().push(getCurrentPath());
    }

    if (goBack.run(args)) {
      goBack.run(root);
    } else {
      return errorManager.getError("Invalid Directory",
          args[0] + "is not a valid directory");
    }


    FileSystem.getStack().push(args[0]);
    Cd newWorkingDirectory = new Cd();
    newWorkingDirectory.run(args);

    return null;
  }
}
