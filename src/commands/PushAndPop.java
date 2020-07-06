package commands;

import commands.DirectoryManager;
import data.FileSystem;
import driver.ErrorHandler;

public class PushAndPop extends DirectoryManager{
	
	public PushAndPop() {
	}

	public void pushPath(String[] args) {
		if (args.length == 0) {
			ErrorHandler error = new ErrorHandler("Invalid Argument");
			return;
		}
		if (FileSystem.getStack().isEmpty()) {
			FileSystem.getStack().push(getCurrentPath());
		}
		FileSystem.getStack().push(args[0]);
		Cd newWorkingDirectory = new Cd(args);
		newWorkingDirectory.run();
	}
	
	public void pop() {
		if (FileSystem.getStack().size() == 0) {
			System.out.println("stack is empty");
			return;
		}
		FileSystem.getStack().pop();
		String []path = {FileSystem.getStack().peek()};
		FileSystem.getStack().remove();
		Cd newWorkingDirectory = new Cd(path);
		newWorkingDirectory.run();
	}
	

}
