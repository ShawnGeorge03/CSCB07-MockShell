package driver;

import java.util.ArrayList;

import sun.jvm.hotspot.runtime.ThreadStackTrace;

import java.io.*;

public class Cd extends DirectoryManager {
	
	ArrayList<String> cd_args = new ArrayList<String>();
	FileSystem filesys = null;
	filesys = FileSystem.getFileSys();
	
	public Cd(ArrayList<String> arguments) {
		this.cd_args = arguments;
	}
	
	public boolean isValid(ArrayList<String> args) {
		String cur = args.get(0);
		
		if ((cur == "." || cur == ".." || cur == "/") && (args.size() == 1)){
			return true;
		}
		else if (cur.matches("[A-Za-z0-9/]") && args.size() == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void run() {
		String argument = this.cd_args.get(0);
		
		if (argument == "..") {
			this.filesys.assignCurrent(this.filesys.current.parent);
		}

		else if (argument == "/") {
			this.filesys.assignCurrent(this.filesys.root);
		}
		
		else {
			String[] split_args = argument.split("/");
			for (int i = 0; i < split_args.length; i++) {
				if (split_args[0] == this.filesys.root.name) {
					//Make path from root
				}
				else {
					//Check relative path
				}	
			}
				
		}
	}
	
	
		
		
	}
	
	
	
	//Access file system and then work on commands from there
	
}
