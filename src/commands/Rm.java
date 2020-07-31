package commands;

import java.util.ArrayList;
import java.util.Arrays;

import data.FileSystemI;
import data.Node;
import errors.InvalidArgsProvidedException;

public class Rm extends DirectoryManager implements CommandI{
	
	/**
	 * Declare instance variable of ArrayList to hold all arguments
	 */
	ArrayList<String> args;
	/**
	 * Declare instance variable of ErrorHandler to handle error messages
	 */
	Cd traverseFileSystem;

	private RedirectionManager rManager;

	public Rm() {
		rManager = new RedirectionManager();
		traverseFileSystem = new Cd();
	}

	@Override
	public boolean checkArgs(FileSystemI fs, String[] arguments, String fullInput) throws InvalidArgsProvidedException {
		ArrayList<String> args = new ArrayList<String>(Arrays.asList(arguments));
		if (args.size() != 1) {
			throw new InvalidArgsProvidedException("Error: Invalid Argument : Expecting 1 Argument only!");
		}else if (args.get(0).equals("/")){
			throw new InvalidArgsProvidedException("Error: Invalid Directory : Cannot remove root directory");
		}
		return true;
	}
	
	
	public String run(FileSystemI filesys, String[] arguments, String fullInput, boolean val) {
		this.args = new ArrayList<String>(Arrays.asList(arguments));

		try {
			rManager.isRedirectionableCommand(filesys, fullInput);
			if(checkArgs(filesys, arguments, fullInput)){
				removeDir(filesys, args.get(0).split(" "), filesys.getCurrentPath().split(" "));
			}
		} catch (InvalidArgsProvidedException e) {
			return e.getLocalizedMessage();
		}
		return null;
	}

	private void removeDir(FileSystemI fs, String[] dirToRemove, String[] currentPath) throws InvalidArgsProvidedException{
		if (traverseFileSystem.run(dirToRemove, fs)){
			if (!fs.getCurrent().getisDir()){
				throw new InvalidArgsProvidedException("Error: Invalid Directory : " + dirToRemove[0] + " is not a directory");
			}
			Node toRemove = fs.getCurrent();
			fs.assignCurrent(fs.getCurrent().getParent());
			for (int i = 0; i < fs.getCurrent().getList().size(); i++){
				if (fs.getCurrent().getList().get(i).equals(toRemove)){
					fs.getCurrent().getList().remove(i);
					traverseFileSystem.run(currentPath, fs);
					return;
				}
			}
		}else{
			throw new InvalidArgsProvidedException("Error: Invalid Directory : " + dirToRemove[0] + " is not a directory");
		}
		return;
	}

}
