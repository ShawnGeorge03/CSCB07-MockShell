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
    this.filesys = FileSystem.getFileSys();
    this.output = "";
  }

  /**
   * Checks if arguments are valid, then sends it to readFile to read the file
   * 
   * @param args the string array of given arguments
   * @param fullInput the string that contains the raw input given to JShell
   * @return the contents of file
   */
  public String run(String[] args, String fullInput, boolean val) {
    if (args.length == 0) {
      return getErrorHandler().getError("No parameters provided", "");
    } else {
      output = "";
      readFile(args);
    }
    return output;
  }

  /**
   * Reads the requested file and gets contents
   * 
   * @param filePaths the path to requested file
   */
  public void readFile(String[] filePaths) {
    Node file = null;
    for (int i = 0; i < filePaths.length; i++) {
      if (filePaths[i].startsWith("C/")) {
        file = findFileGivenAbsolute(filePaths[i]);
      } else {
        file = findFileGivenRelative(filePaths[i]);
      }

      if (file != null) {
        output += filesys.getContent(file);
      } else {
        output += getErrorHandler().getError("File Not Found", filePaths[i]);
      }

      if (!(filePaths.length == 1 || i == filePaths.length - 1))
        output += "\n" + "\n" + "\n";
    }
  }


}
