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
    filesys = FileSystem.getFileSys();
    errorManager = new ErrorHandler();
  }

  /**
   * Getter method to access the ErrorHandler object
   */
  public ErrorHandler getErrorHandler() {
    return this.errorManager;
  }

  /**
   * Method that searches the filesystem for a specific file given a relative path. Returns the node
   * that holds the file if found, otherwise it will return null
   * 
   * @param fileName String that stores the relative path of the file we wish to search for
   * @return Node will return the node that holds the file or will return null if not found
   */
  public Node findFileGivenRelative(String fileName) {
    Node current = filesys.getCurrent();
    String[] givenPath = fileName.split("/");
    if (givenPath.length == 1)
      return checkList(current, fileName);

    for (int i = 0; i < givenPath.length; i++) {
      if ((i + 1) == givenPath.length) {
        return checkList(current, givenPath[i]);
      } else {
        current = findInDirectory(givenPath[i], current);
        if (current == null)
          return null;
      }
    }

    return null;
  }

  private Node checkList(Node current, String fileName) {
    for (int i = 0; i < current.getList().size(); i++) {
      if (current.getList().get(i).getName().equals(fileName)
          && !current.getList().get(i).isDir())
        return current.getList().get(i);
    }
    return null;
  }

  private Node findInDirectory(String file, Node current) {
    for (int i = 0; i < current.getList().size(); i++) {
      if (current.getList().get(i).getName().equals(file))
        return current.getList().get(i);
    }
    return null;
  }

  /**
   * Method that returns the absolute path of the current working directory
   * 
   * @return String returns the full absolute path of the current working directory
   */
  public String getCurrentPath() {
    Node current = this.filesys.getCurrent();
    Node parent = current.getParent();

    if (parent == null)
      return "C/";
    String currentPath = parent.getName() + "/" + current.getName();
    parent = parent.getParent();
    while (parent != null) {
      currentPath = parent.getName() + "/" + currentPath;
      parent = parent.getParent();
    }

    return currentPath;

  }

  /**
   * Method that searches the filesystem for a specific folder given an absolute path. Returns the
   * node that holds the folder if found, otherwise it will return null
   * 
   * @param absolutePath String that stores the relative path of the folder we wish to search for
   * @return Node will return the node that holds the folder or will return null if not found
   */
  public Node findFolderGivenAbsolute(String absolutePath) {
    Node current = filesys.getRoot();
    String[] directories = absolutePath.split("/");

    for (int i = 1; i < directories.length; i++) {
      for (int j = 0; j < current.getList().size(); j++) {
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
   * @param absolutePath String that stores the absolute path of the file we wish to search for
   * @return Node will return the node that holds the file or will return null if not found
   */
  public Node findFileGivenAbsolute(String absolutePath) {
    Node current = filesys.getRoot();
    String[] directories = absolutePath.split("/");

    for (int i = 1; i < directories.length; i++) {
      if ((i + 1) == directories.length) {
        return checkList(current, directories[i]);
      } else {
        Node nextFolder = findInDirectory(directories[i], current);
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
   * @param fileName String that stores the file name that the user inputted
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
