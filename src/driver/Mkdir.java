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
				String[] currentPath = {getCurrentPath()};
				String[] newArgs = {args.get(0).substring(0, args.get(0).lastIndexOf('/'))};
				Cd newpath = new Cd(newArgs);
				newpath.run();
				
				Node newNode = new Node();
				newNode.content = null;
				newNode.isDir = true;
				newNode.name = args.get(0).substring(args.get(0).lastIndexOf('/') + 1);
				
				filesys.addToDirectory(newNode);
				
				Cd goBack = new Cd(currentPath);
				goBack.run();
				return;
			}
			else {
				Node newNode = new Node();
				newNode.content = null;
				newNode.isDir = true;
				newNode.name = args.get(0);
				
				filesys.addToDirectory(newNode);
				return;
			}
		}else {
				//RETURN ERROR CLASS	
		}
	}
	
	private boolean checkValidArgs() {
		return args.size() == 1;
	}
	
	private boolean checkPath() {
		return args.get(0).contains("/");
	}
}
