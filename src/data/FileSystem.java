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
package data;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * Class FileSystem hosts the data structure required to contain all directory and file nodes
 */
public class FileSystem implements FileSystemI{

  /**
   * Declare instance variable FileSystem which is yet to be intialized
   */
  static FileSystem fileSys = null;
  /**
   * Declare instance variable of ArrayList to contain the logs of all inputs given to JShell
   */
  private static ArrayList<String> CommandLog;
  /**
   * Declare instance variable of ArrayDeque
   */
  private static Deque<String> stack;

  /**
   * Initialize current and root nodes
   */
  Node root;
  Node current;

  private ArrayList<String> path;

  /**
   * Constructor for FileSystem to set root default values
   */
  private FileSystem() {
    CommandLog = new ArrayList<String>();
    stack = new ArrayDeque<String>();
    path = new ArrayList<String>();
    root = new Node.Builder(true, "/").setRoot(true).setParent(null).build();
    current = root;
  }

  /**
   * Returns FileSystem of object, keep in mind that it is returning the same object unless being
   * called for the first time
   * 
   * @return the filesystem object
   */
  public static FileSystem getFileSys() {
    if (fileSys == null) {
      fileSys = new FileSystem();
    }
    return fileSys;
  }

  // Trivial methods below that require no JavaDoc
  public Node getRoot() {
    return root;
  }

  public Node getCurrent() {
    return current;
  }

  public String getContent(Node file) {
    return file.getContent();
  }

  public void assignCurrent(Node currentDirectory) {
    current = currentDirectory;
  }

  public void addToDirectory(Node newNode) {
    newNode.setParent(current);
    current.getList().add(newNode);
  }
  
  public void removeFromDirectory(int i) {
	  current.getList().remove(i);
  }

  public ArrayList<String> getCommandLog() {
    return CommandLog;
  }

  public Deque<String> getStack() {
    return stack;
  }

  public void setStack(Deque<String> stack) {
    FileSystem.stack = stack;
  }

    /**
   * Method that checks if the file name that the user inputted is a valid file name. If the file name
   * contains illegal characters then this method returns false. If the file name does not contain any
   * illegal characters then it returns true.
   * 
   * @param fileName  String that stores the file name that the user inputted
   * @return boolean false if file name contains illegal characters, otherwise returns true
   */
  public boolean isValidName(String fileName) {
    String[] invalidChars = {"/", ".", "\\s+", "!", "@", "#", "$", "%", "^",
        "&", "*", "(", ")", "{", "}", "~", "|", "<", ">", "?"};
    for (int i = 0; i < invalidChars.length; i++) {
      if (fileName.contains(invalidChars[i])) {
        return false;
      }
    }
    return true;
  }

  /**
   * Finds the current working directory
   * 
   * @return the path to the current directory
   */
  public String getCurrentPath() {
    String output = "";
    Node current = fileSys.getCurrent();

    if (current.equals(fileSys.getRoot())) {
      return fileSys.getRoot().getName();
    }

    path.add(current.getName());
    while (current != fileSys.getRoot()) {
      current = current.getParent();
      path.add(current.getName());
    }

    int pathLength = path.size();
    while (pathLength > 0) {
      output = output.concat(path.get(pathLength - 1) + "/");
      pathLength--;
    }

    path.clear();

    return output.substring(1, output.length() - 1);
  }

  @Override
  public Node createFile(String fileContents, String fileName, String filePath) {
    return current;
  }

  @Override
  public Node findFile(String filePath) {
    String absolutePath = filePath;

    //If the given path is a relative path then make it a absolute path
    if(!filePath.startsWith("/")){
      absolutePath = (getCurrentPath() + "/" + filePath).substring(1);
    }
    
    //Grabs root directory
    Node current = getRoot();
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

  @Override
  public void fileAppend(String content, String file) {

  }

  @Override
  public void fileOverwrite(String content, String file) {

  }

}
