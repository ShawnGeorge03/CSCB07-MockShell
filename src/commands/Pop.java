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
   * Run method checks if the user has given any arguments, and removes the top of the stack
   * Returns string output, either an error message or if performed without error then will return
   * null
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
   * Pop takes the current stack and removes the very top of the stack
   * 
   * @return String  if performed without error then will return null else, it will return error
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
