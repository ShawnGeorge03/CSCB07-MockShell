package commands;

import java.util.ArrayList;
import data.FileSystem;
import data.Node;

public class DirectoryManager {

  ArrayList<String> path = new ArrayList<String>();
  /**
   * Declare instance of FileSystem
   */
  FileSystem filesys = null;
  /**
   * Declare instance of ErrorHandler
   */
  ErrorHandler error = new ErrorHandler();

  public DirectoryManager() {
    filesys = FileSystem.getFileSys();
  }

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

  public int checkDirExists(ArrayList<Node> toCheck, String dir) {
    for (int i = 0; i < toCheck.size(); i++) {
      if ((toCheck.get(i)).getName().equals(dir)) {
        return i;
      }
    }
    return -1;
  }

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
          System.out.println(error.getError("Invalid Directory", path));
        }
      } else {
        System.out.println(error.getError("Directory Not Found", path));
        return false;
      }
    }
    filesys.assignCurrent(currNode);
    return true;
  }

  public boolean makeRelativePath(String path) {
    String[] pathArr = path.split("/");
    Node currNode = filesys.getCurrent();
    for (int i = 0; i < pathArr.length; i++) {
      int indexOfDir = checkDirExists(currNode.getList(), pathArr[i]);
      if (indexOfDir != -1) {
        if (currNode.getList().get(indexOfDir).isDir()) {
          currNode = currNode.getList().get(indexOfDir);
        } else {
          System.out.println(error.getError("Invalid Directory", path));
          return false;
        }
      } else {
        System.out.println(error.getError("Directory Not Found", path));
        return false;
      }
    }
    filesys.assignCurrent(currNode);
    return true;
  }

}
