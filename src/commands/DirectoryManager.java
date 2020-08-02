// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: santhso7
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

import java.util.ArrayList;

import data.FileSystemI;
import data.Node;

/**
 * Class DirectoryManager which provides helper methods for other directory-related commands creating new paths
 */
public class DirectoryManager {

  /**
   * Constructor for DirectoryManager which initializes filesystem
   */
  public DirectoryManager() {

  }

  
  /**
   * Checks if requested directory exists in the ArrayList of nodes in the current node
   * 
   * @param toCheck ArrayList of Nodes that contain all of a Node's children
   * @param dir the requested directory to check
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
   * @param path the path that the user is requesting
   * @return true if path was made successfully, false otherwise
   */
  public boolean makePathFromRoot(String[] pathArr, FileSystemI filesys) {
    Node currNode = filesys.getRoot();
    // Loop through pathArr which is array of all directories in the path and change
    // directory to
    // the
    // last one (if its a valid path)
    for (int i = 0; i < pathArr.length; i++) {
      if (pathArr[i].equals("")) {
        continue;
      }
      if (pathArr[i].equals("..")) {
        if (currNode.getParent() != null) {
          currNode = currNode.getParent();
        }
      }
      int indexOfDir = checkDirExists(currNode.getList(), pathArr[i]);
      if (indexOfDir != -1) {
        if (currNode.getList().get(indexOfDir).getisDir()) {
          currNode = currNode.getList().get(indexOfDir);
        } else {

          return false;
        }
      } else {
        return false;
      }
    }
    filesys.assignCurrent(currNode);
    return true;
  }

  /**
   * Makes relative path from current directory
   * 
   * @param path the path that the user is requesting
   * @return true if path was made successfully, false otherwise
   */
  public boolean makeRelativePath(String[] pathArr, FileSystemI filesys) {
    Node currNode = filesys.getCurrent();
    // Loop through pathArr which is array of all directories in the path and change
    // directory to
    // the last one (if its a valid path)
    for (int i = 0; i < pathArr.length; i++) {
      if (pathArr[i].equals("..")) {
        if (currNode.getParent() != null) {
          currNode = currNode.getParent();
        }
        continue;
      }
      int indexOfDir = checkDirExists(currNode.getList(), pathArr[i]);
      if (indexOfDir != -1) {
        if (currNode.getList().get(indexOfDir).getisDir()) {
          currNode = currNode.getList().get(indexOfDir);
        } else {
          return false;
        }
      } else {
        return false;
      }
    }
    filesys.assignCurrent(currNode);
    return true;
  }
}
