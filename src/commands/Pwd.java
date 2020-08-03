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
 * Class Pwd is responsible for providing the absolute path of the current
 * working directory
 */
public class Pwd extends DirectoryManager implements CommandI {

  /**
   * Declare instance variable of RedirectionManager to handle the redirection operations
   */
  RedirectionManager redirect;

  /**
   * Declare instance variable of String to hold the output of the command (error if error occurs else null)
   */
  String output;

  /**
   * Constructor for Pwd that initializes the ErrorHandler object
   */
  public Pwd() {
    this.redirect = new RedirectionManager();
  }

  /**
   * Run method first checks to see if the user has inputted any arguments, else it will return the
   * absolute path to the current working directory
   * 
   * @param args  the string array of arguments
   * @param fullInput  the full line of input that the user gives into JShell
   * @param val  stores a boolean value
   * @return String holding the absolute path to the current working directory, or an error message
   */
  public String run(FileSystemI filesys, String[] args, String fullInput, boolean val) {
    String[] arguments =  redirect.setParams(fullInput);

    try {
      if (checkArgs(arguments)) {
        output = redirect.outputResult(filesys, runPwd(filesys, arguments));
      }
    } catch (InvalidArgsProvidedException e) {
      return e.getLocalizedMessage();
    }
    return output;
  }

  /**
   * Checks if the arguments provided by the user follows the requirements or else throw an exception
   * 
   * @param filesys  refrence of FileSystemI object (MockFileSystem or FileSystem)
   * @param arguments the list of arguments from user which may contain a redirection error
   * @param fullInput the user input
   * 
   * @throws InvalidArgsProvidedException
   * 
   * @return true if the parameter meet requirements and false if not
   */
  @Override
  public boolean checkArgs(FileSystemI fs, String[] arguments, String fullInput) 
    throws InvalidArgsProvidedException {
		return false;
	}

  private boolean checkArgs(String[] arguments) throws InvalidArgsProvidedException { 
    if(String.join(" ", arguments).contains("Error : No parameters provided")){
      throw new InvalidArgsProvidedException("Error : No parameters provided");
    }else if(String.join(" ", arguments).contains("Error : Multiple Parameters have been provided")){
      throw new InvalidArgsProvidedException(String.join(" ", arguments));
    }
    return true;
  }

  private String runPwd(FileSystemI filesys, String[] args) throws InvalidArgsProvidedException{
    // If the user provides any input for the following function
    if (args.length != 0) {
      // Returns an Invalid arguments error
      throw new InvalidArgsProvidedException("Error: Invalid Argument : pwd doesn't take any arguments");

    }
    String currentPath = filesys.getCurrentPath();
    // Returns the current working directory the user is in
    return currentPath;
  }

}
