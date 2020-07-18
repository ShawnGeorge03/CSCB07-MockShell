package commands;

import java.util.ArrayList;
import java.util.Arrays;

import data.FileSystem;
import data.Node;

public class Rm extends DirectoryManager implements CommandI{
	
	/**
	 * Declare instance variable of ArrayList to hold all arguments
	 */
	ArrayList<String> args;
	/**
	 * Declare instance variable of ErrorHandler to handle error messages
	 */
	private ErrorHandler error;
	
	public Rm() {
		error = new ErrorHandler();
	}
	
	
	public String run(String[] arguments, String actualInput, boolean val) {
		this.args = new ArrayList<String>(Arrays.asList(arguments));
		if (args.size() != 1) {
			return error.getError("Invalid Argument", "Expecting 1 Argument only!");
		}
		
		if (args.get(0).contains("/")) {
			String[] currentPath = {getCurrentPath()};
			String dirToRemove = args.get(0).substring(args.get(0).lastIndexOf("/") + 1);
			String[] dirAbove = {args.get(0).substring(0, args.get(0).lastIndexOf("/"))};
			Cd traverseFileSystem = new Cd();
			
			if (traverseFileSystem.run(dirAbove)) {
				System.out.println(getCurrentPath());
				Node current = FileSystem.getFileSys().getCurrent();
				for (int i = 0; i < current.getList().size(); i++) {
					if (current.getList().get(i).getName().equals(dirToRemove)) {
						FileSystem.getFileSys().removeFromDirectory(i);
						traverseFileSystem.run(currentPath);
						return null;
					}
				}
				return error.getError("Directory Not Found", dirToRemove + " was not found!");
			}else {
				return error.getError("Invalid Directory", dirAbove[0]  + "is not a valid path/directory");
			}
		}else {
			String dirToRemove = args.get(0);
			Node current = FileSystem.getFileSys().getCurrent();
			
			for (int i = 0; i < current.getList().size(); i++) {
				if (current.getList().get(i).getName().equals(dirToRemove)) {
					FileSystem.getFileSys().removeFromDirectory(i);
					return null;
				}
			}
			return error.getError("Directory Not Found", dirToRemove + " was not found!");
		}
		
	}

}
