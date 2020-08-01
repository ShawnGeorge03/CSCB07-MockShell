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
import errors.DirectoryException;
import errors.InvalidArgsProvidedException;

/**
 * Class Push is responsible forSaves the current working directory to 
 * the directory stack and changes the current working directory to 
 * the one the user gave if it is valid
 */
public class Push extends DirectoryManager implements CommandI {

  private Cd goBack;
  RedirectionManager rManager;
  
  public Push() {
    this.goBack = new Cd();
    rManager = new RedirectionManager();
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
  public String run(FileSystemI filesys, String fullInput, boolean val) {
    //Gets the current path
    String current = filesys.getCurrentPath();
    //Seperates the parameters from everything else from the user input
    String[] args = rManager.setParams(fullInput);
    try {
      if (rManager.isRedirectionableCommand(fullInput));

      if(checkArgs(filesys, args, fullInput)){
        runPush(filesys, args, current);
      }
    } catch (InvalidArgsProvidedException e) {
      return e.getLocalizedMessage();
    }

    return null;
  }
  
  @Override
	public boolean checkArgs(FileSystemI fs, String[] arguments, String fullInput) throws InvalidArgsProvidedException {
    if (arguments.length == 0) {
      throw new InvalidArgsProvidedException("Error : No parameters provided");
    }else if(arguments.length > 1){
      String parameter = String.join(" ", arguments);
      throw new InvalidArgsProvidedException("Error : Multiple Parameters have been provided : " 
                    + parameter + " Only 1 valid directory path is required");
    }
    return true;
  }

  private void runPush(FileSystemI filesys, String[] args, String current) throws DirectoryException{
    if (goBack.run(args, filesys)) {
      filesys.getStack().push(current);
    } else {
      throw new DirectoryException("Error: Invalid Directory : " + args[0] + " is not a valid directory");
    }  
    return;  
  }
}
