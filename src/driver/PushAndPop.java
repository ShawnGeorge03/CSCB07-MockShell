package driver;

public class PushAndPop extends DirectoryManager{
	
	public PushAndPop() {
	}

	public void pushPath(String[] args) {
		if (args.length == 0) {
			return;
		}
		if (FileSystem.stack.isEmpty()) {
			FileSystem.stack.push(getCurrentPath());
		}
		FileSystem.stack.push(args[0]);
		Cd newWorkingDirectory = new Cd(args);
		newWorkingDirectory.run();
	}
	
	public void pop() {
		if (FileSystem.stack.size() == 0) {
			System.out.println("stack is empty");
			return;
		}
		FileSystem.stack.pop();
		String []path = {FileSystem.stack.peek()};
		FileSystem.stack.remove(0);
		Cd newWorkingDirectory = new Cd(path);
		newWorkingDirectory.run();
	}
	

}
