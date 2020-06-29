package driver;


public class FileManager {
  
  public FileSystem filesys;
  
  public FileManager() {
    filesys = FileSystem.getFileSys();
  }
  
  public Node findFileGivenRelative(String fileName) {
    if(!fileName.contains(".txt"))
      fileName += ".txt";
    
    if(filesys.getCurrent().parent != null) {
      Node parent = filesys.getCurrent().parent;
      for(int i = 0; i < parent.list.size(); i++) {
        if(parent.list.get(i).name.equals(fileName)) 
          return parent.list.get(i);
      } 
    }
    else {
      Node current = filesys.getCurrent();
      for(int i = 0; i < current.list.size(); i++) {
        if(current.list.get(i).name.equals(fileName) && !current.list.get(i).isDir) 
          return current.list.get(i);
      }
    }
    return null;
  }
  
  private boolean findInDirectory(String file) {
    if(filesys.getCurrent().parent != null) {
      Node parent = filesys.getCurrent().parent;
      for(int i = 0; i < parent.list.size(); i++) {
        if(parent.list.get(i).name.equals(file)) 
          return true;
      } 
    }
    else {
      Node current = filesys.getCurrent();
      for(int i = 0; i < current.list.size(); i++) {
        if(current.list.get(i).name.equals(file)) 
          return true;
      }
    }
    return false;
  }
  
  public Node findFileGivenAbsolute(String absolutePath) {
    Node current = filesys.getRoot();
    String[] directories = absolutePath.split("/");
    
    for(int i = 1; i < directories.length; i++) {
      if(directories[i].contains(".txt")) 
        if((i+1) >= directories.length)
          return findFileGivenRelative(directories[i]);
        else System.out.println("Error"); //Invalid path, file cannot have more shit in it
      else {
        if(findInDirectory(directories[i])) {
          
          //int index = current.list.indexOf();
          //current = current.list.get(index);
        }
        else return null;
      }
    }
    return null;
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
