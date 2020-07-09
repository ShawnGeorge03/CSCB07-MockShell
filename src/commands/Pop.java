package commands;

import data.FileSystem;

/**
 * Class Pop is responsible for
 */
public class Pop extends DirectoryManager implements CommandI {
  /**
   * Declare instance variable of ErrorHandler to handle error messages
   */
  private ErrorHandler error;

  /**
   * Constructor for Pop that initializes the ErrorHandler
   */
  public Pop() {
    this.error = new ErrorHandler();
  }

  /**
   * Method Description
   * 
   * @param args  The string array of all arguments
   * @param fullInput  The full input provided by the user
   * @param val  A boolean for speak mode
   * @return String  An error message, else null
   */
  public String run(String[] args, String fullInput, boolean val) {
    if (args.length != 0) {
      return error.getError("Invalid Argument", "No arguments should be given");
    }

    String output = pop();
    return output;
  }

  /**
   * Method Description
   * 
   * @return String ....
   */
  public String pop() {
    FileSystem.getStack().pop();
    if (FileSystem.getStack().size() == 0) {
      return "Stack is empty";
    }
    String[] path = {FileSystem.getStack().peek()};
    Cd newWorkingDirectory = new Cd();
    newWorkingDirectory.run(path);
    return null;
  }
}
