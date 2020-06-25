package driver;

import java.util.ArrayList;

public class DirectoryManager {
  
  FileSystem filesys = null;
  ArrayList<String> path = new ArrayList<String>();
  
  public DirectoryManager() {
    filesys = FileSystem.getFileSys();
  }
  
  public String getCurrentPath() {
    String output = "/";
    Node curr = filesys.getCurrent();
    if (curr.name == "/") {
      return "/";
    }
    path.add(curr.name);
    while (curr.name != "/") {
      curr = curr.parent;
      path.add(curr.name);
    }
    
    int i = path.size();
    while (i > 0) {
      output = output.concat(path.get(i-1) + "/");
      i--;
    }

    return output.substring(0,output.length()-1);
  }
  
  public Node makePath(String path) {
    String[] pathArr = path.split("/");
    Node currNode = filesys.getRoot();
    for (int i = 0; i < pathArr.length; i++) {
      currNode.list.a
      currNode = currNode.list.get(currNode.list.size()-1);
    }
  }
  
  

}
