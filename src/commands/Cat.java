 package commands;
 
 import data.FileSystem;
 import data.Node;

public class Cat extends FileManager {
  
  FileSystem filesys;
  private ErrorHandler error;
  
  public Cat() {
    this.filesys = FileSystem.getFileSys();
    this.error = new ErrorHandler();
  }
  
  public void readFile(String[] filePaths) {   
   Node file = null;
    for(int i = 0; i < filePaths.length; i++) {
      if(filePaths[i].startsWith("C/")) {
        file = findFileGivenAbsolute(filePaths[i]);
      }else {
        file = findFileGivenRelative(filePaths[i]);
      }
      
      if(file != null) {
        System.out.println(filesys.getContent(file));
      }else {
        error.getError("File Not Found", filePaths[i]);
      }
      
      if(!(filePaths.length == 1 || i == filePaths.length - 1 )) {
        System.out.println("\r\n" + "\r\n" );
      }

    }
  }
}