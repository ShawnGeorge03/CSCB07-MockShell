package commands;

import data.FileSystem;
import data.Node;

/**
 * Class Cat views the contents of requested file
 */
public class Cat extends FileManager implements CommandI {

  /**
   * Declare instance variable of FileSystem to access the filesystem
   */
  FileSystem filesys;

  /**
   * Declare instance variable of String to contain the output that Cat may return
   */
  String output;

  /**
   * Constructor for Cat that initializes instance variables
   */
  public Cat() {
    // Get the current FileSystem
    this.filesys = FileSystem.getFileSys();
    // Initializing the String object output
    this.output = "";
  }

  /**
   * Checks if arguments are valid, then sends it to readFile to read the file
   * 
   * @param args  the string array of given arguments
   * @param fullInput  the string that contains the raw input given to JShell
   * @param val  stores a boolean value
   * @return the contents of file
   */
  public String run(String[] args, String fullInput, boolean val) {
    if (args.length == 0) {
      // Returns an error of No parameters provided
      return getErrorHandler().getError("No parameters provided", "");
    } else {
      // Initializing the String object output after each time the method is called
      output = "";
      // Calls the readFile function to return what is in the file
      readFile(args);
    }
    // Returns the output for the arguments
    return output;
  }

  private void readFile(String[] filePaths) {
    // Declares and initialized a Node to null
    Node file = null;
    // Runs through all the filePaths and stores the output for each case
    for (int i = 0; i < filePaths.length; i++) {
      // If the file path is an absolute path
      if (filePaths[i].startsWith("C/")) {
        // Calls the following method to return a reference to that specific Node
        file = findFileGivenAbsolute(filePaths[i]);
        // If the file path is a relative path
      } else {
        // Calls the following method to return a reference to that specific Node
        file = findFileGivenRelative(filePaths[i]);
      }

      // If the file does exist
      if (file != null) {
        // Collect and append the text to the String object output
        output += filesys.getContent(file);
        // If the file does not exist
      } else {
        // Collect and append the error of File Not Found
        output += getErrorHandler().getError("File Not Found", filePaths[i]);
      }

      // If it is not one file or it is the last file in the filePaths
      if (!(filePaths.length == 1 || i == filePaths.length - 1))
        // Append multiple line breaks to the String object output
        output += "\n" + "\n" + "\n";
    }
  }
}
