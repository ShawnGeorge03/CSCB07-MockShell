package driver;

import java.util.ArrayList;

public class Pwd extends DirectoryManager{
	
	public void printDirectory() {
		Node current = filesys.getCurrent();
		
		ArrayList<String> workingDirectory = null;
		
		if (current.name == "/") {
			System.out.println(current.name);
			return;
		}
		
		while(current.name != "/") {
			workingDirectory.add(current.name);
			current = current.parent;
		}
		
		String output = "";
		
		for (int i = workingDirectory.size(); i > -1; i--) {
			output += workingDirectory.get(i) + "/";
		}
		
		System.out.println(output);
	}
}
