package driver;

public class Cat {
  
  FileSystem filesys;
  DirectoryManager DirecMang;
  
  public Cat() {
    this.filesys = FileSystem.getFileSys();
    this.DirecMang = new DirectoryManager();
  }
  
  public void readFile(String filePaths) {
    String[] fileNames = ((String) filePaths.subSequence(
        filePaths.indexOf("cat") + 4, filePaths.length())).split("\\s+");
    
    if(fileNames.length == 0) {System.out.println("InvalidParameterErorr");}
    
    System.out.println(DirecMang.getCurrentPath());
       
  }
}
