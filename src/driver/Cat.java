 package driver;

public class Cat extends FileManager {
  
  FileSystem filesys;
  
  public Cat() {
    this.filesys = FileSystem.getFileSys();
  }
  
  public void readFile(String[] filePaths) {
    for(int i = 0; i < filePaths.length; i++) {
      Node file = find_file(filePaths[i]);
      if(file != null) {
        System.out.println(file.content);
      }else{
        System.out.println("File Not Found");
      }
    }
  }
}
