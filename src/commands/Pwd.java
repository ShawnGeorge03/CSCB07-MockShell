package commands;

import commands.DirectoryManager;

public class Pwd extends DirectoryManager implements CommandI {
  
  public String run(String[] args, String fullInput) {
	  return this.getCurrentPath();
  }
 
}
