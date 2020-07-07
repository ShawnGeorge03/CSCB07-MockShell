package commands;

import data.FileSystem;
import data.Node;

public class FileManager {
  
  public FileSystem filesys;
  
  public FileManager() {
    filesys = FileSystem.getFileSys();
  }
  
  public Node findFileGivenRelative(String fileName) {
 
    Node current = filesys.getCurrent();
    return checkList(current, fileName);
    
    /*for(int i = 0; i < current.list.size(); i++) {
      if(current.list.get(i).name.equals(fileName) && !current.list.get(i).isDir) 
        return current.list.get(i);
    }
    return null;
    */
    /* PLEASE LEAVE ON 
    if(filesys.getCurrent().parent != null) {
      Node parent = filesys.getCurrent().parent;
      System.out.println(parent.name);
      for(int i = 0; i < parent.list.size(); i++) {
        //System.out.println(parent.list.get(i).name);
        if(parent.list.get(i).name.equals(fileName)) 
          return parent.list.get(i);
      } 
    }
    else {
      Node current = filesys.getCurrent().parent;
      for(int i = 0; i < current.list.size(); i++) {
        if(current.list.get(i).name.equals(fileName) && !current.list.get(i).isDir) 
          return current.list.get(i);
      }
    }*/
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
    
    for(int i = 1; i < directories.length; i++) { //loop through the directory
         
      if((i+1) == directories.length) {
        return checkList(current, directories[i]);
      }
      else if(ifFile(directories[i])) System.out.println("Error -> File Cannot Contain More Files");
      else {
        Node temp = findInDirectory(directories[i]);
        if(temp != null) current = temp;
        else return null;
      }
      
      
      /*if(directories[i].contains(".txt")){ 
        if((i+1) >= directories.length) {
          return checkList(current, directories[i]);*/
          /*Node found = null;
          for(int j = 0; j < current.list.size(); j++) {
            if(current.list.get(j).name.equals(directories[i]) && !current.list.get(j).isDir) {
              found = current.list.get(j);
              break;
            }
          }
          return found;*/
       /* }
        else System.out.println("Error -> File Cannot Contain More Files");
      }
      else {
        Node temp = findInDirectory(directories[i]);
        if(temp != null) current = temp;
        else return null;
      }*/
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
