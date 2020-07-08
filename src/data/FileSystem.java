package data;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * Class FileSystem hosts the data structure required to contain all directory and file nodes
 */
public class FileSystem {
  
  /**
   * Declare instance variable FileSystem which is yet to be intialized
   */
  static FileSystem fileSys = null;
  /**
   * Declare instance variable of ArrayList to contain the logs of all inputs given to JShell
   */
  private static ArrayList<String> CommandLog = new ArrayList<String>();
  /**
   * Declare instance variable of ArrayDeque
   */
  private static Deque<String> stack = new ArrayDeque<String>();

  /**
   * Initialize current and root nodes
   */
  Node root = new Node();
  Node current = new Node();

  /**
   * Constructor for FileSystem to set root default values
   */
  private FileSystem() {
    root.setDir(true);
    root.setContent(null);
    root.setName("/");
    root.isRoot = true;
    root.setParent(null);
    current = root;
  }

  /**
   * Returns FileSystem of object, keep in mind that it is returning the same object 
   * unless being called for the first time
   * 
   * @return the filesystem object
   */
  public static FileSystem getFileSys() {
    if (fileSys == null) {
      fileSys = new FileSystem();
      return fileSys;
    } else {
      return fileSys;
    }
  }

  //Trivial methods below that require no JavaDoc
  public Node getRoot() {
    return root;
  }

  public Node getCurrent() {
    return current;
  }

  public String getContent(Node file) {
    return file.content;
  }

  public void assignCurrent(Node currentDirectory) {
    current = currentDirectory;
  }

  public void addToDirectory(Node newNode) {
    newNode.setParent(current);
    current.getList().add(newNode);
  }

  public static ArrayList<String> getCommandLog() {
    return CommandLog;
  }

  public static void setCommandLog(ArrayList<String> commandLog) {
    CommandLog = commandLog;
  }

  public static Deque<String> getStack() {
    return stack;
  }

  public static void setStack(Deque<String> stack) {
    FileSystem.stack = stack;
  }

}
