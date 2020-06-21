package driver;

import java.util.ArrayList;

public class Pwd extends DirectoryManager{
	
	public void printDirectory() {
		FileSystem filesys = null;
		filesys = FileSystem.getFileSys();
		
		Node current = filesys.getCurrent();
		
		ArrayList<String> workingDirectory = null;
		
		while(current != null) {
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
