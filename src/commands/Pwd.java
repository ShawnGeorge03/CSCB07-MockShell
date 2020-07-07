package commands;

import commands.DirectoryManager;

public class Pwd extends DirectoryManager implements CommandI {
  
  
  public void run(String[] args, String fullInput) {
    printDirectory();
  }

  public void printDirectory() {
    System.out.println(this.getCurrentPath());
  }

 
}
