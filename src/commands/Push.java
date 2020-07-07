package commands;

import commands.DirectoryManager;
import data.FileSystem;

public class Push extends DirectoryManager implements CommandI {
  private ErrorHandler error;

  public Push() {
    this.error = new ErrorHandler();
  }
  
  
  public String run(String[] args, String fullInput) {
    pushPath(args);
    return null;
  }

  public void pushPath(String[] args) {
    if (args.length == 0) {
      error.getError("Invalid Argument",  Integer.toString(args.length) + " arguments, expecting 1 argument");
      return;
    }
    
    if (FileSystem.getStack().isEmpty()) {
      FileSystem.getStack().push(getCurrentPath());
    }
    FileSystem.getStack().push(args[0]);
    Cd newWorkingDirectory = new Cd();
    newWorkingDirectory.run(args);
  }
}
