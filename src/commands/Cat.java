 package commands;
 
 import data.FileSystem;
 import data.Node;

public class Cat extends FileManager implements CommandI {
  
  FileSystem filesys;
  private ErrorHandler error;
  
  String output;
  
  public Cat() {
    this.filesys = FileSystem.getFileSys();
    this.error = new ErrorHandler();
    this.output = "";
  }
  
  public String run(String[] args, String fullInput) {
    readFile(args);
    return output;
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
        output += filesys.getContent(file);
      }else {
        output += error.getError("File Not Found", filePaths[i]);
      }
      
      if(!(filePaths.length == 1 || i == filePaths.length - 1 )) 
        output += "\n" + "\n" + "\n";
    }
  }


}