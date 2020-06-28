package driver;

import java.util.ArrayList;
import java.util.Arrays;

public class Mkdir extends DirectoryManager{
	ArrayList<String> args;
	
	public Mkdir(String[] args) {
		this.args = new ArrayList<String>(Arrays.asList(args)); 
	}
	
	public void MakeDirectory(){
		
		if (checkValidArgs()) {
			if (checkPath()) {
				String[] newArgs = {args.get(0)}
				Cd newpath = new Cd(newArgs);
				newpath.run();
			}
			else {
				Node newNode = new Node();
				newNode.content = null;
				newNode.isDir = true;
				newNode.name = args.get(0);
				
				filesys.addToDirectory(newNode);
			}
		}else {
				//RETURN ERROR CLASS	
		}
	}
	
	private boolean checkValidArgs() {
		return args.size() == 1;
	}
	
	private boolean checkPath() {
		return args.contains('/');
	}
}
