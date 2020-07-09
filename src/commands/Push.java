package commands;

import commands.DirectoryManager;
import data.FileSystem;
import data.Node;

/**
 * Class Push is responsible for
 */
public class Push extends DirectoryManager implements CommandI {

  /**
   * Declare an instance of ErrorHandler to handle any errors that occur
   */
  private ErrorHandler error;

  /**
   * Constructor for Push that initializes the ErrorHandler object
   */
  public Push() {
    this.error = new ErrorHandler();
  }

  /**
   * Method Description
   * 
   * @param args  the string array of arguments
   * @param fullInput  the full line of input that the user gives into JShell
   * @return String  An error message if any error, else null
   */
  public String run(String[] args, String fullInput, boolean val) {
    Cd goBack = new Cd();
    String[] root = {FileSystem.getFileSys().getRoot().getName()};

    if (args.length != 1) {
      return error.getError("Invalid Argument",
          Integer.toString(args.length) + " arguments, expecting 1 argument");
    }

    if (FileSystem.getStack().isEmpty()) {
      FileSystem.getStack().push(getCurrentPath());
    }

    if (goBack.run(args)) {
      goBack.run(root);
    } else {
      return error.getError("Invalid Directory",
          args[0] + "is not a valid directory");
    }


    FileSystem.getStack().push(args[0]);
    Cd newWorkingDirectory = new Cd();
    newWorkingDirectory.run(args);

    return null;
  }
}
