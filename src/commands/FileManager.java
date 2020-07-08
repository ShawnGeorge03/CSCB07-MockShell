package commands;

import data.FileSystem;
import data.Node;

public class FileManager {
  
  public FileSystem filesys;
  protected ErrorHandler error;
  
  public FileManager() {
    filesys = FileSystem.getFileSys();
    error = new ErrorHandler();
  }
  
  public ErrorHandler getErrorHandler() {
    return this.error;
  }
  
  public Node findFileGivenRelative(String fileName) {
    Node current = filesys.getCurrent();
    String[] path = fileName.split("/");
    if(path.length == 1)
      return checkList(current, fileName);
    
    for(int i = 0; i < path.length; i++) {
      if((i+1) == path.length) {
        return checkList(current, path[i]);
      }
      else {
        current = findInDirectory(path[i], current);
        if(current == null) return null;
      }
    }
    
    return null;
  }
  
  private Node checkList(Node current, String fileName) {
    for(int i = 0; i < current.getList().size(); i++) {
      if(current.getList().get(i).getName().equals(fileName) && !current.getList().get(i).isDir()) 
        return current.getList().get(i);
    }
    return null;
  }
  
  private Node findInDirectory(String file, Node current) {
    for(int i = 0; i < current.getList().size(); i++) {
      if(current.getList().get(i).getName().equals(file)) 
          return current.getList().get(i);
    }
    return null;
  }
  
  public String getCurrentPath() {
    Node current = this.filesys.getCurrent();
    Node parent = current.getParent();
    
    if(parent == null) return "C/";
    String path = parent.getName() + "/" + current.getName();
    parent = parent.getParent();
    while(parent != null)
    {
      path = parent.getName() + "/" + path;
      parent = parent.getParent();
    }
    
    return path;
     
  }
  
  public Node findFolderGivenAbsolute(String absolutePath) {
    Node current = filesys.getRoot();
    String[] directories = absolutePath.split("/");    
    
    for(int i = 1; i < directories.length; i++) { 
       for(int j = 0; j < current.getList().size(); j++) {
         if(current.getList().get(j).getName().equals(directories[i])) {
           current = current.getList().get(j);
           break;
         }
       }
    }
    return current;
  }
  
  public Node findFileGivenAbsolute(String absolutePath) {
    Node current = filesys.getRoot();
    String[] directories = absolutePath.split("/");    
    
    for(int i = 1; i < directories.length; i++) { 
      if((i+1) == directories.length) {
        return checkList(current, directories[i]);
      }
      else {
        Node temp = findInDirectory(directories[i], current);
        if(temp != null) current = temp;
        else return null;
      }
    }
    return current;
  }
  
  public boolean isValidFileName(String fileName) {
    String[] invalidChars = {"/", ".", "\\s+", "!", "@", "#", "$", "%", "^", "&"
        , "*", "(", ")", "{", "}", "~", "|", "<", ">", "?"};
    for(int i = 0; i < invalidChars.length; i++) {
      if(fileName.contains(invalidChars[i])) {
        return false;
      }
    }
    return true;
  }
  
}
