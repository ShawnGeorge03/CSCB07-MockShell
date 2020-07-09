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

import data.FileSystem;
import data.Node;

/**
 * Class FileManager is responsible for handling FileSystem related operations needed for echo and
 * cat
 */
public class FileManager {

  /** Declare instance of FileSystem to access the current file system */
  public FileSystem filesys;

  /** Declare instance of ErrorHandler to handle any errors */
  protected ErrorHandler errorManager;

  /**
   * Constructor for FileManager that initializes the ErrorHandler object and FileSystem object
   */
  public FileManager() {
    //Get the current FileSystem
    filesys = FileSystem.getFileSys();
    //Initializes the ErrorHandler object
    errorManager = new ErrorHandler();
  }

  public ErrorHandler getErrorHandler() {
    return this.errorManager;
  }

  /**
   * Method that searches the filesystem for a specific file given a relative path. Returns the node
   * that holds the file if found, otherwise it will return null
   * 
   * @param fileName  String that stores the relative path of the file we wish to search for
   * @return Node will return the node that holds the file or will return null if not found
   */
  public Node findFileGivenRelative(String fileName) {
    //Grabs current folder/directory
    Node current = filesys.getCurrent();
    //Splits the fileName to get the individual folder paths
    String[] givenPath = fileName.split("/");
    //If only the file name was inputted
    if (givenPath.length == 1)
      //Looks for the file in the current directory
      return checkList(current, fileName);
   
    for (int i = 0; i < givenPath.length; i++) {
      //The very last index should hold the name of the file
      if ((i + 1) == givenPath.length) {
        //Look for and return the file
        return checkList(current, givenPath[i]);
      } else {
        //Enter the next folder
        current = findInDirectory(givenPath[i], current);
        //If the folder was not found then return null
        if (current == null)
          return null;
      }
    }
    return null;
  }

  private Node checkList(Node current, String fileName) {
    //Loops through the directories and checks if the file exists
    for (int i = 0; i < current.getList().size(); i++) {
      //If file name matches the desired file then return the node
      if (current.getList().get(i).getName().equals(fileName)
          && !current.getList().get(i).isDir())
        return current.getList().get(i);
    }
    //Return null if not found
    return null;
  }

  private Node findInDirectory(String file, Node current) {
    //Loops through the directories and checks if the folder exists
    for (int i = 0; i < current.getList().size(); i++) {
      //If folder name matches the desired folder then return the node
      if (current.getList().get(i).getName().equals(file))
        return current.getList().get(i);
    }
    //Return null if not found
    return null;
  }

  /**
   * Method that returns the absolute path of the current working directory
   * 
   * @return String returns the full absolute path of the current working directory
   */
  public String getCurrentPath() {
    //Grabs the current directory
    Node current = this.filesys.getCurrent();
    //Grabs the parent of the current directory
    Node parent = current.getParent();

    //If we are in the root folder
    if (parent == null)
      return current.getName() + "/";
    //Create the absolute path
    String currentPath = parent.getName() + "/" + current.getName();
    parent = parent.getParent();
    while (parent != null) {
      currentPath = parent.getName() + "/" + currentPath;
      parent = parent.getParent();
    }
    //Return the final absolute path
    return currentPath;

  }

  /**
   * Method that searches the filesystem for a specific folder given an absolute path. Returns the
   * node that holds the folder if found, otherwise it will return null
   * 
   * @param absolutePath  String that stores the relative path of the folder we wish to search for
   * @return Node will return the node that holds the folder or will return null if not found
   */
  public Node findFolderGivenAbsolute(String absolutePath) {
    //Grabs root directory
    Node current = filesys.getRoot();
    //Splits the absolutePath into the individual folders
    String[] directories = absolutePath.split("/");
    
    //Loops through the directories array
    for (int i = 1; i < directories.length; i++) {
      //Loops through the ArrayList of directories
      for (int j = 0; j < current.getList().size(); j++) {
        //If the folder matches the one we need then return it
        if (current.getList().get(j).getName().equals(directories[i])) {
          current = current.getList().get(j);
          break;
        }
      }
    }
    return current;
  }

  /**
   * Method that searches the filesystem for a specific file given an absolute path. Returns the node
   * of the file if found, otherwise it will return null
   * 
   * @param absolutePath  String that stores the absolute path of the file we wish to search for
   * @return Node will return the node that holds the file or will return null if not found
   */
  public Node findFileGivenAbsolute(String absolutePath) {
    //Grabs root node
    Node current = filesys.getRoot();
    //Splits the absolutePath into the individual folders
    String[] directories = absolutePath.split("/");
    
    //Loops through the individual directories
    for (int i = 1; i < directories.length; i++) {
      //The file we want is in the last index of the array
      if ((i + 1) == directories.length) {
        return checkList(current, directories[i]);
      } else {
        //Find the next folder we need to go in
        Node nextFolder = findInDirectory(directories[i], current);
        //Set current to nextFolder
        if (nextFolder != null)
          current = nextFolder;
        else
          return null;
      }
    }
    return current;
  }

  /**
   * Method that checks if the file name that the user inputted is a valid file name. If the file name
   * contains illegal characters then this method returns false. If the file name does not contain any
   * illegal characters then it returns true.
   * 
   * @param fileName  String that stores the file name that the user inputted
   * @return boolean false if file name contains illegal characters, otherwise returns true
   */
  public boolean isValidFileName(String fileName) {
    String[] invalidChars = {"/", ".", "\\s+", "!", "@", "#", "$", "%", "^",
        "&", "*", "(", ")", "{", "}", "~", "|", "<", ">", "?"};
    for (int i = 0; i < invalidChars.length; i++) {
      if (fileName.contains(invalidChars[i])) {
        return false;
      }
    }
    return true;
  }

}
