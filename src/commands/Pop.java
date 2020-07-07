package commands;

import data.FileSystem;

public class Pop implements CommandI {
	private ErrorHandler error;
	
	public Pop() {
	    this.error = new ErrorHandler();
	  }
	
	  public void run(String[] args, String fullInput) {
	    pop();
	  }
	
	public void pop() {
	    if (FileSystem.getStack().size() == 0) {
	      System.out.println("stack is empty");
	      return;
	    }
	    FileSystem.getStack().pop();
	    String[] path = {FileSystem.getStack().peek()};
	    FileSystem.getStack().remove();
	    Cd newWorkingDirectory = new Cd();
	    newWorkingDirectory.run(path);
	  }
}
