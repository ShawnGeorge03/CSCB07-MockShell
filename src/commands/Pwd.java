package commands;

import commands.DirectoryManager;

public class Pwd extends DirectoryManager implements CommandI {
  
  ErrorHandler error;

  public Pwd() {
    this.error = new ErrorHandler();
  }
  
  public String run(String[] args, String fullInput) {
      if (args.length != 0) {
          return error.getError("Invalid Argument", "pwd doesn't take any arguments");
      }
      return this.getCurrentPath();
  }
  
 
}
