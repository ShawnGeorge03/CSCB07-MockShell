package driver;


public class FileManager {
  
  public FileSystem filesys;
  
  public FileManager() {
    filesys = FileSystem.getFileSys();
  }
  
  public Node findFileGivenRelative(String fileName) {
    if(!fileName.contains(".txt"))
      fileName += ".txt";
    
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
    for(int i = 0; i < current.list.size(); i++) {
      if(current.list.get(i).name.equals(fileName) && !current.list.get(i).isDir) 
        return current.list.get(i);
    }
    return null;
  }
  
  private Node findInDirectory(String file) {
    if(filesys.getCurrent().parent != null) {
      Node parent = filesys.getCurrent().parent;
      for(int i = 0; i < parent.list.size(); i++) {
        if(parent.list.get(i).name.equals(file)) 
          return parent.list.get(i);
      } 
    }
    else {
      Node current = filesys.getCurrent();
      for(int i = 0; i < current.list.size(); i++) {
        if(current.list.get(i).name.equals(file)) 
          return current.list.get(i);
      }
    }
    return null;
  }
  
  public Node findFileGivenAbsolute(String absolutePath) {
    Node current = filesys.getRoot();
    String[] directories = absolutePath.split("/");    
    
    for(int i = 1; i < directories.length; i++) {
      if(directories[i].contains(".txt")){ 
        if((i+1) >= directories.length) {
          return checkList(current, directories[i]);
          /*Node found = null;
          for(int j = 0; j < current.list.size(); j++) {
            if(current.list.get(j).name.equals(directories[i]) && !current.list.get(j).isDir) {
              found = current.list.get(j);
              break;
            }
          }
          return found;*/
        }
        else System.out.println("Error -> File Cannot Contain More Files");
      }
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
