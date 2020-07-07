package commands;

import commands.DirectoryManager;
import data.FileSystem;
import data.Node;

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
    if (args.length != 1) {
      error.getError("Invalid Argument",  Integer.toString(args.length) + " arguments, expecting 1 argument");
      return;
    }
    
    if (FileSystem.getStack().isEmpty()) {
      FileSystem.getStack().push(getCurrentPath());
    }
    
    Node Current = FileSystem.getFileSys().getCurrent();
    if (args[0].substring(0, 1).equals("/")) {
    	if (makePathFromRoot(args[0])) {
    		FileSystem.getFileSys().assignCurrent(Current);
    	}else {
    		System.out.println(error.getError("Invalid Directory", args[0] + "is not a valid directory"));
    		return;
    	}
    }else {
    	if (makeRelativePath(args[0])) {
    		FileSystem.getFileSys().assignCurrent(Current);
    	}else {
    		System.out.println(error.getError("Invalid Directory", args[0] + "is not a valid directory"));
    		return;
    	}
    }
    
    FileSystem.getStack().push(args[0]);
    Cd newWorkingDirectory = new Cd();
    newWorkingDirectory.run(args);
  }
}
