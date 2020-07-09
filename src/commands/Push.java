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
