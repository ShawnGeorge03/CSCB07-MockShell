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
    return checkList(current, fileName);
  }
  
  private Node checkList(Node current, String fileName) {
    for(int i = 0; i < current.getList().size(); i++) {
      if(current.getList().get(i).getName().equals(fileName) && !current.getList().get(i).isDir()) 
        return current.getList().get(i);
    }
    return null;
  }
  
  private Node findInDirectory(String file) {
    if(filesys.getCurrent().getParent() != null) {
      Node parent = filesys.getCurrent().getParent();
      for(int i = 0; i < parent.getList().size(); i++) {
        if(parent.getList().get(i).getName().equals(file)) 
          return parent.getList().get(i);
      } 
    }
    else {
      Node current = filesys.getCurrent();
      for(int i = 0; i < current.getList().size(); i++) {
        if(current.getList().get(i).getName().equals(file)) 
          return current.getList().get(i);
      }
    }
    return null;
  }
  
  private boolean ifFile(String fileName) {
    Node dir = filesys.getRoot();
    for(int i = 0; i < dir.getList().size(); i++) {
      if(dir.getList().get(i).getName().equals(fileName) && !dir.getList().get(i).isDir()) 
        return true;
    }
    return false;
  }
  
  public Node findFileGivenAbsolute(String absolutePath) {
    Node current = filesys.getRoot();
    String[] directories = absolutePath.split("/");    
    
    for(int i = 1; i < directories.length; i++) { 
      if((i+1) == directories.length) {
        return checkList(current, directories[i]);
      }
      else if(!ifFile(directories[i])) System.out.println("Error -> File Cannot Contain More Files");
      else {
        Node temp = findInDirectory(directories[i]);
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
