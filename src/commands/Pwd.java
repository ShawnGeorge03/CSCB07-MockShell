package commands;

import commands.DirectoryManager;

public class Pwd extends DirectoryManager implements CommandI {
  
  
  public String run(String[] args, String fullInput) {
    printDirectory();
    return null;
  }

  public void printDirectory() {
    System.out.println(this.getCurrentPath());
  }

 
}
