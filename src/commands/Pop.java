package commands;

import data.FileSystem;

public class Pop extends DirectoryManager implements CommandI {
	private ErrorHandler error;
	
	public Pop() {
	    this.error = new ErrorHandler();
	  }
	
	  public String run(String[] args, String fullInput) {
	    String output = pop();
	    return output;
	  }
	
	public String pop() {
		FileSystem.getStack().pop();
	    if (FileSystem.getStack().size() == 0) {
	      System.out.println("Stack is empty");
	      return "Stack is empty";
	    }
	    String[] path = {FileSystem.getStack().peek()};
	    Cd newWorkingDirectory = new Cd();
	    newWorkingDirectory.run(path);
	    return getCurrentPath();
	  }
}
