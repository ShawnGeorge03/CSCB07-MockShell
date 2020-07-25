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

/**
 * Class FileManager is responsible for handling FileSystem related operations needed for echo and
 * cat
 */
public class FileManager {


  /** Declare instance of ErrorHandler to handle any errors */
  protected ErrorHandler errorManager;

  /**
   * Constructor for FileManager that initializes the ErrorHandler object and FileSystem object
   */
  public FileManager() {
    //Initializes the ErrorHandler object
    errorManager = new ErrorHandler();
  }

  public ErrorHandler getErrorHandler() {
    return this.errorManager;
  }

}
