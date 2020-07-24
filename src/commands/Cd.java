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

import data.FileSystemI;

/**
 * Class Cd is responsible for changing directories within the FileSystem
 */
public class Cd extends DirectoryManager implements CommandI {

  boolean successfulPath = false;

  /**
   * Declare instance of ErrorHandler to handle error messages
   */
  ErrorHandler error;

  /**
   * Constructor for class Cd which initalizes instance variables
   */
  public Cd() {
    error = new ErrorHandler();
  }

  /**
   * Starting run method which checks if arguments were given, then passes it to another run method
   * which processes the command
   * 
   * @param args the string array of arguments
   * @param fullInput the full line of input that the user gives into JShell
   * @return any error messages if there are any
   */
  public String run(FileSystemI filesys, String[] args, String fullInput, boolean val) {
    if (args.length == 0) {
      return error.getError("No parameters provided", "");
   } else if (args.length > 1) {
      return error.getError("Mulptile parameters provided", 
          fullInput.substring(fullInput.indexOf("cd") + 2).trim());
    }

    // If the change of directory was unsuccessful, then an error msg is returned
    if (!run(args, filesys)) {
     return error.getError("Invalid Directory",
          fullInput.substring(fullInput.indexOf("cd") + 2).trim());
    }
    return null;
  }

  /**
   * Main run method that executes the performance of changing directories based on what argument is
   * given. If argument is ".", nothing happens If argument is "..", go up one directory If argument
   * is "/", change the cd to root If argument is absolute path, check if the path exists, then
   * change to that directory If argument is relative path, check if that path exists, then change
   * to that directory
   * 
   * @param arguments the array of arguments provided by user
   * @return true if the argument was processed and the change of directory was successful
   */
  public boolean run(String[] arguments, FileSystemI filesys) {
    // Initializing variables
    String[] splitArgs = arguments[0].split("/");
    // Changing to root
    if (splitArgs.length == 0 && arguments[0].length() == 1) {
      filesys.assignCurrent(filesys.getRoot());
      return true;
    }
    if (splitArgs.length == 1) {
      successfulPath = run_1_arg(splitArgs[0], splitArgs, filesys);
    } else if (splitArgs.length > 1) {
      successfulPath = run_more_args(splitArgs, filesys);
    }
    return successfulPath;
  }

  private boolean run_1_arg(String argument, String[] splitArgs, FileSystemI filesys) {
    // Processes change of directory with all the one argument options
    if (argument.equals("..")) {
      // Traverse one directory up
      if (filesys.getCurrent().getName().equals(filesys.getRoot().getName())) {
        return true;
      }
      filesys.assignCurrent(filesys.getCurrent().getParent());
      return true;
    } else if (argument.equals(".")) {
      return true;
    } else {
      // Relative path to requested dir
      successfulPath = this.makeRelativePath(splitArgs, filesys);
    }
    return successfulPath;
  }

  private boolean run_more_args(String[] splitArgs, FileSystemI filesys) {
    // If there are more arguments, it will create a relative path depending on the
    // first element
    // being a root or not
    if (splitArgs[0].equals("")) {
      successfulPath = this.makePathFromRoot(splitArgs, filesys);
    } else {
      successfulPath = this.makeRelativePath(splitArgs, filesys);
    }
    return successfulPath;
  }

}
