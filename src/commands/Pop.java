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
import errors.InvalidArgsProvidedException;

/**
 * Class Pop is responsible for popping the top most directory 
 * on the directory stack onto the working directory of the 
 * user
 */
public class Pop extends DirectoryManager implements CommandI {

  RedirectionManager rManager;

  public Pop() {
    rManager = new RedirectionManager();
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
    try {
      if (rManager.isRedirectionableCommand(filesys, fullInput));
      if(checkArgs(filesys, args, fullInput)){
        String[] path = {filesys.getStack().pop()};
        Cd newWorkingDirectory = new Cd();
        newWorkingDirectory.run(path, filesys);      
      }
    } catch (InvalidArgsProvidedException e) {
      return e.getLocalizedMessage();
    }
    return null;
  }

  @Override
	public boolean checkArgs(FileSystemI fs, String[] arguments, String fullInput) throws InvalidArgsProvidedException {
    if(arguments.length > 0){
      throw new InvalidArgsProvidedException("Error: Invalid Argument : No arguments should be given");
    } else if(fs.getStack().size() == 0){
      throw new InvalidArgsProvidedException("Error: Stack is empty");
    }
    return true;
  }
  
}
