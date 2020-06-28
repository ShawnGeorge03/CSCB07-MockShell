package driver;

import java.util.ArrayList;
import java.util.Arrays;

public class Ls extends DirectoryManager{
	ArrayList<String> args;
	
	public Ls(String[] args) {
		this.args = new ArrayList<String>(Arrays.asList(args)); 
	}
	
	public void listDirectory() {
		if (args.size() == 0) {
			Node curr = filesys.getCurrent();
			for (int i = 0; i < curr.list.size(); i++) {
				if (curr.list.get(i).isDir) {
					System.out.println(curr.list.get(i).name);
				} else {
					//System.out.println(curr.list.get(i).name + ".txt");
	                System.out.println(curr.list.get(i).name);
				}
			}
		}
	}
}
