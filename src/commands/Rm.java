package commands;

import java.util.ArrayList;
import java.util.Arrays;

import data.FileSystemI;
import data.Node;
import errors.InvalidArgsProvidedException;
import errors.InvalidRedirectionError;

public class Rm extends DirectoryManager implements CommandI{
	
	/**
	 * Declare instance variable of ArrayList to hold all arguments
	 */
	ArrayList<String> args;
	/**
	 * Declare instance variable of ErrorHandler to handle error messages
	 */
	String[] currentPath = {""};
	String[] dirToRemove = {""};
	Cd traverseFileSystem;
	FileSystemI fs;

	private ErrorHandler error;
	private RedirectionManager rManager;

	public Rm() {
		error = new ErrorHandler();
		rManager = new RedirectionManager();
		traverseFileSystem = new Cd();
	}

	@Override
	public boolean checkArgs(FileSystemI fs, String[] arguments, String fullInput) throws InvalidArgsProvidedException {
		return false;
	}
	
	
	public String run(FileSystemI filesys, String[] arguments, String fullInput, boolean val) {
		this.args = new ArrayList<String>(Arrays.asList(arguments));
		fs = filesys;
		currentPath[0] = fs.getCurrentPath();

		try {
			rManager.isRedirectionableCommand(filesys, fullInput);
		  } catch (InvalidRedirectionError e) {
			return e.getLocalizedMessage();
		  }
		
		  if (args.size() != 1) {
			return error.getError("Invalid Argument", "Expecting 1 Argument only!");
		}
		if (args.get(0).equals("/")){
			return error.getError("Invalid Directory", "Cannot remove root directory");
		}

		dirToRemove[0] = args.get(0);

		if (traverseFileSystem.run(dirToRemove, fs)){
			if (!fs.getCurrent().getisDir()){
				return error.getError("Invalid Directory", dirToRemove[0] + " is not a directory");
			}
			Node toRemove = fs.getCurrent();
			fs.assignCurrent(fs.getCurrent().getParent());
			for (int i = 0; i < fs.getCurrent().getList().size(); i++){
				if (fs.getCurrent().getList().get(i).equals(toRemove)){
					fs.getCurrent().getList().remove(i);
					traverseFileSystem.run(currentPath, fs);
					return null;
				}
			}

			
		}else{
			return error.getError("Invalid Directory", dirToRemove[0] + " is not a directory");
		}
		return null;
	}

}
