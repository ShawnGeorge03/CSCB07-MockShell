package driver;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;

public class PushAndPop {
	
	public PushAndPop() {
	}

	public void pushPath(String[] args) {
		if (args.length == 0) {
			return;
		}
		FileSystem.stack.add(0, args[0]);
		Cd newWorkingDirectory = new Cd(args);
		newWorkingDirectory.run();
	}
	
	public void pop() {
		if (FileSystem.stack.size() == 0) {
			System.out.println("stack is empty");
			return;
		}
		String []path = {FileSystem.stack.get(0)};
		FileSystem.stack.remove(0);
		Cd newWorkingDirectory = new Cd(path);
		newWorkingDirectory.run();
	}
	

}
