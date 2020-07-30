package commands;

import java.util.ArrayList;
import java.util.Arrays;

import data.FileSystemI;
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
	private RedirectionManager rManager;
	public Rm() {
		error = new ErrorHandler();
		rManager = new RedirectionManager();
	}
	
	
	public String run(FileSystemI filesys, String[] arguments, String fullInput, boolean val) {
		this.args = new ArrayList<String>(Arrays.asList(arguments));

		String output = rManager.isRedirectionableCommand(filesys, fullInput);

		if(!"true".equals(output)) return output;

		if (args.size() != 1) {
			return error.getError("Invalid Argument", "Expecting 1 Argument only!");
		}
		
		if (args.get(0).contains("/")) {
			String[] currentPath = {filesys.getCurrentPath()};
			String dirToRemove = args.get(0).substring(args.get(0).lastIndexOf("/") + 1);
			String[] dirAbove = {args.get(0).substring(0, args.get(0).lastIndexOf("/"))};
			Cd traverseFileSystem = new Cd();
			
			if (traverseFileSystem.run(dirAbove, filesys)) {
				Node current = filesys.getCurrent();
				for (int i = 0; i < current.getList().size(); i++) {
					if (current.getList().get(i).getName().equals(dirToRemove)) {
						filesys.removeFromDirectory(i);
						traverseFileSystem.run(currentPath, filesys);
						return null;
					}
				}
				return error.getError("Directory Not Found", dirToRemove + " was not found!");
			}else {
				return error.getError("Invalid Directory", dirAbove[0]  + "is not a valid path/directory");
			}
		}else {
			String dirToRemove = args.get(0);
			Node current = filesys.getCurrent();
			
			for (int i = 0; i < current.getList().size(); i++) {
				if (current.getList().get(i).getName().equals(dirToRemove)) {
					filesys.removeFromDirectory(i);
					return null;
				}
			}
			return error.getError("Directory Not Found", dirToRemove + " was not found!");
		}
		
	}

}
