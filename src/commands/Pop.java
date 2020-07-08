package commands;

import data.FileSystem;

public class Pop extends DirectoryManager implements CommandI {
	private ErrorHandler error;
	
	public Pop() {
	    this.error = new ErrorHandler();
	  }
	
	  public String run(String[] args, String fullInput, boolean val) {
		if (args.length != 0) {
			return error.getError("Invalid Argument", "No arguments should be given");
		}
	    String output = pop();
	    return output;
	  }
	
	public String pop() {
		FileSystem.getStack().pop();
	    if (FileSystem.getStack().size() == 0) {
	      return "Stack is empty";
	    }
	    String[] path = {FileSystem.getStack().peek()};
	    Cd newWorkingDirectory = new Cd();
	    newWorkingDirectory.run(path);
	    return null;
	  }
}
