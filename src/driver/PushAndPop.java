package driver;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;

public class PushAndPop {
	
	static PushAndPop PushPop = null;
	static Deque<String> stack = new ArrayDeque<String>();
	
	private PushAndPop() {
	}
	
	public static PushAndPop getStack() {
		if (PushPop == null) {
			PushPop = new PushAndPop();
			return PushPop;
		} else {
			return PushPop;
		}
	}
	
	public void pushPath(String[] args) {
		if (args.length == 0) {
			return;
		}
		stack.push(args[0]);
		Cd newWorkingDirectory = new Cd(args);
		newWorkingDirectory.run();
	}
	
	public void pop() {
		if (stack.isEmpty()) {
			System.out.println("stack is empty");
			return;
		}
		String []path = {stack.pop()};
		Cd newWorkingDirectory = new Cd(path);
		newWorkingDirectory.run();
	}
	

}
