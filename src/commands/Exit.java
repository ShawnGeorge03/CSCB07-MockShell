// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: santhos7
// UT Student #: 1006094673
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
 * Class Exit handles exiting the JShell
 */
public class Exit implements CommandI {

  //
  private RedirectionManager rManager;

  /**
   * 
   */
  public Exit() {
    this.rManager = new RedirectionManager();
  }

  /**
   * Ends program
   * 
   * @param args      the string array with all arguments
   * @param fullInput the string that contains the raw input provided to JShell
   * @return null no matter what
   */
  @Override
  public String run(FileSystemI filesys,  String[] args,  String fullInput, boolean val) {
    //Seperates the parameters from everything else from the user input
    
    String[] arguments = rManager.setParams(fullInput);
    try {
      rManager.isRedirectionableCommand(fullInput);

      if (checkArgs(filesys, arguments, fullInput)) {
        // Exits the session of the Shell
        System.exit(0);
      }
    } catch (InvalidArgsProvidedException e) {
      return e.getLocalizedMessage();
    } 
 
    return null;
  }

  
  
  @Override
  public boolean checkArgs(FileSystemI fs, String[] arguments, String fullInput) throws InvalidArgsProvidedException {
    if(arguments.length != 0) 
      throw new InvalidArgsProvidedException("Error : Arguments not required : " + 
                  fullInput.substring(fullInput.indexOf("exit ") + 4).trim());
    return true;
  }
  
}
