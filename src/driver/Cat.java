 package driver;

public class Cat extends FileManager {
  
  FileSystem filesys;
  DirectoryManager manager;
  
  public Cat() {
    this.filesys = FileSystem.getFileSys();
    this.manager = new DirectoryManager();

  }
  
  public void readFile(String[] filePaths) {   
    for(int i = 0; i < filePaths.length; i++) {
      Node file = findFileGivenRelative(formatFilePath(filePaths[i]));
      if(file != null) {
        System.out.println(file.content);
      }else{
        System.out.println("File Not Found");
      }
    }
  }
  
  public String formatFilePath(String filePaths) {
    if(!filePaths.startsWith("C/")) {
       return filePaths;
    }else {
      return filePaths.substring(filePaths.lastIndexOf("/") + 1);
    }   
  }
}
