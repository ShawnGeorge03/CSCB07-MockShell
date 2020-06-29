package driver;

public class Pwd extends DirectoryManager {
  
  FileSystem filesys = null;

  public void printDirectory() {
    filesys = FileSystem.getFileSys();
    System.out.println(this.getCurrentPath());
  }
}
