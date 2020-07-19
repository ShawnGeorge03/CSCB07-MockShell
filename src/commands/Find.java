package commands;

import java.util.ArrayList;

import data.FileSystem;

public class Find extends DirectoryManager implements CommandI {

  /**
   * Declare instance of FileSystem so we can access the filesystem
   */
  FileSystem filesys;
  /**
   * Declare instance of ErrorHandler to handle error messages
   */
  ErrorHandler error;
  public Find() {
    
  }

  @Override
  public String run(String[] args, String fullInput, boolean val) {
    args = fullInput.split(" ");
    ArrayList<String> paths = new ArrayList<String>();
    for (int i = 0; i < args.length; i++) {
      paths.add(args[i]);
      if (args[i+1].equals("-type")) {
        break;
      }
    }
    System.out.println(args);
    System.out.println("-----");
    System.out.println(paths);
    
    return null;
  }
  
  public static void main(String[] args) {
    Find f = new Find();
    f.run(null, "/users/Desktop /pictures/boi -type f", true);
  }
  

}
