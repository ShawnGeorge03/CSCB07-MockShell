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
}
