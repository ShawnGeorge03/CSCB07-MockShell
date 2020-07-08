package commands;

import java.util.ArrayList;
import data.FileSystem;
import data.Node;
/**
 * Class DirectoryManager which provides helper methods for other directory-related commands
 * such as getting the current path and creating new paths
 */
public class DirectoryManager {

  ArrayList<String> path = new ArrayList<String>();
  /**
   * Declare instance of FileSystem to access filesystem
   */
  FileSystem filesys = null;
  /**
   * Declare instance of ErrorHandler to handle error messages
   */
  ErrorHandler error = new ErrorHandler();
  
  /**
   * Constructor for DirectoryManager which initializes filesystem
   */
  public DirectoryManager() {
    filesys = FileSystem.getFileSys();
  }
  
  /**
   * Finds the current path within the FileSystem
   * 
   * @return the path to the current directory
   */
  public String getCurrentPath() {
    String output = "";
    Node curr = filesys.getCurrent();

    if (curr.equals(this.filesys.getRoot())) {
      return this.filesys.getRoot().getName();
    }

    path.add(curr.getName());
    while (curr != this.filesys.getRoot()) {
      curr = curr.getParent();
      path.add(curr.getName());
    }

    int i = path.size();
    while (i > 0) {
      output = output.concat(path.get(i - 1) + "/");
      i--;
    }

    path.clear();

    return output.substring(0, output.length() - 1);
  }

  /**
   * Checks if requested directory exists in the ArrayList of nodes in the current node
   * 
   * @param toCheck  ArrayList of Nodes that contain all of a Node's children
   * @param dir  the requested directory to check
   * @return the index of the requested directory if it exists, -1 otherwise
   */
  public int checkDirExists(ArrayList<Node> toCheck, String dir) {
    for (int i = 0; i < toCheck.size(); i++) {
      if ((toCheck.get(i)).getName().equals(dir)) {
        return i;
      }
    }
    return -1;
  }
  
  /**
   * Makes path from the root
   * 
   * @param path  the path that the user is requesting
   * @return true if path was made successfully, false otherwise
   */
  public boolean makePathFromRoot(String path) {
    String[] pathArr = path.split("/");
    Node currNode = filesys.getRoot();
    for (int i = 0; i < pathArr.length; i++) {
      if (pathArr[i].equals(filesys.getRoot().getName())) {
        continue;
      }
      int indexOfDir = checkDirExists(currNode.getList(), pathArr[i]);
      if (indexOfDir != -1) {
        if (currNode.getList().get(indexOfDir).isDir()) {
          currNode = currNode.getList().get(indexOfDir);
        } else {
          //System.out.println(error.getError("Invalid Directory", path));
        }
      } else {
        //System.out.println(error.getError("Directory Not Found", path));
        return false;
      }
    }
    filesys.assignCurrent(currNode);
    return true;
  }

  /**
   * Makes relative path from current directory
   * 
   * @param path  the path that the user is requesting
   * @return true if path was made successfully, false otherwise
   */
  public boolean makeRelativePath(String path) {
    String[] pathArr = path.split("/");
    Node currNode = filesys.getCurrent();
    for (int i = 0; i < pathArr.length; i++) {
      int indexOfDir = checkDirExists(currNode.getList(), pathArr[i]);
      if (indexOfDir != -1) {
        if (currNode.getList().get(indexOfDir).isDir()) {
          currNode = currNode.getList().get(indexOfDir);
        } else {
          //System.out.println(error.getError("Invalid Directory", path));
          return false;
        }
      } else {
        //System.out.println(error.getError("Directory Not Found", path));
        return false;
      }
    }
    filesys.assignCurrent(currNode);
    return true;
  }

}
