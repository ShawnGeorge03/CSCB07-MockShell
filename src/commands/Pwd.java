package commands;

import commands.DirectoryManager;

public class Pwd extends DirectoryManager implements CommandI {
  
  public String run(String[] args, String fullInput) {
	  if (args.length != 0) {
		  ErrorHandler error = new ErrorHandler();
		  System.out.println(error.getError("Invalid Argument", "pwd doesn't take any arguments"));
		  return null;
	  }
	  return this.getCurrentPath();
  }
 
}
