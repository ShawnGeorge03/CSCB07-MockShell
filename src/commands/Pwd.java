package commands;

import commands.DirectoryManager;

public class Pwd extends DirectoryManager implements CommandI {
  
  
  public String run(String[] args, String fullInput) {
    return printDirectory();
  }

  public String printDirectory() {
    //System.out.println(this.getCurrentPath());
    return this.getCurrentPath();
  }

 
}
