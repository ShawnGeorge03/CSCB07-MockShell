package commands;

import commands.DirectoryManager;

/**
 * Class Pwd is responsible for providing the absolute path of the current working directory
 */
public class Pwd extends DirectoryManager implements CommandI {
  
  /**
   * Declare instance of ErrorHandler to handle any errors that occur
   */
  ErrorHandler error;

  /**
   * Constructor for Pwd that initializes the ErrorHandler object
   */
  public Pwd() {
    //Initializes a ErrorHandler Object
    this.error = new ErrorHandler();
  }
  
  /**
   * Run method first checks to see if the user has inputted any arguments, else it will return the 
   * absolute path to the current working directory
   * 
   * @param args  the string array of arguments
   * @param fullInput  the full line of input that the user gives into JShell
   * @param val stores a boolean value
   * @return String holding the absolute path to the current working directory, or an error message
   */
  public String run(String[] args, String fullInput, boolean val) {
    //If the user provides any input for the following function
      if (args.length != 0) {
        //Returns an Invalid arguments error
          return error.getError("Invalid Argument", "pwd doesn't take any arguments");
      }
      //Returns the current working directory the user is in
      return this.getCurrentPath();
  }
  
 
}
