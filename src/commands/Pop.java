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
 * Class Pop is responsible for popping the top most directory 
 * on the directory stack onto the working directory of the 
 * user
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
  public String run(FileSystemI filesys, String[] args, String fullInput, boolean val) {
    if (args.length != 0) {
      return error.getError("Invalid Argument", "No arguments should be given");
    }

    String output = pop(filesys);
    return output;
  }

  /**
   * Pop takes the current stack and removes the very top of the stack
   * 
   * @return String  if performed without error then will return null else, it will return error
   */
  public String pop(FileSystemI filesys) {
    if (filesys.getStack().size() == 0) {
      return "Stack is empty";
    }

    String[] path = {filesys.getStack().pop()};
    Cd newWorkingDirectory = new Cd();
    newWorkingDirectory.run(path, filesys);
    return null;
  }
}
