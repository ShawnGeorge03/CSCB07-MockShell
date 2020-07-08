package commands;

import commands.DirectoryManager;
import data.FileSystem;
import data.Node;

/**
 * Class Push is responsible for 
 */
public class Push extends DirectoryManager implements CommandI {
  
  /**
   * Declare an instance of ErrorHandler to handle any errors that occur
   */
  private ErrorHandler error;

  /**
   * Constructor for Pwd that initializes the ErrorHandler object
   */
  public Push() {
    this.error = new ErrorHandler();
  }
  
  /**
   * Method Description
   * 
   * @param args  the string array of arguments
   * @param fullInput  the full line of input that the user gives into JShell
   * @return String holding the absolute path to the current working directory, or an error message
   */
  public String run(String[] args, String fullInput) {
    pushPath(args);
    return null;
  }

  /**
   * Method Description
   * 
   * @param args  the string array of arguments
   * @return null 
   */
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
