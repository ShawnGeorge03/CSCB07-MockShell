package driver;

import java.util.ArrayList;
import java.util.Arrays;

public class Mkdir extends DirectoryManager{
	ArrayList<String> args;
	
	public Mkdir(String[] args) {
		this.args = new ArrayList<String>(Arrays.asList(args)); 
	}
	
	public void MakeDirectory(){
		FileSystem filesys = null;
		filesys = FileSystem.getFileSys();
		
		System.out.println(args);
		
		// filesys.addToDirectory(newNode);
	}
}
