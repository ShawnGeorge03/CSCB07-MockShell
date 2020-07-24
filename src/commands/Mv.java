package commands;

import java.util.ArrayList;
import java.util.Arrays;

import data.FileSystemI;
import data.Node;

public class Mv extends DirectoryManager implements CommandI{
	
	/**
	 * Declare instance variable of ArrayList to hold all arguments
	 */
	ArrayList<String> args;
	/**
	 * Declare instance variable of ErrorHandler to handle error messages
	 */
	private ErrorHandler error;
	
	public Mv() {
		error = new ErrorHandler();
	}
	
	public String run(FileSystemI filesys, String[] arguments, String actualInput, boolean val) {
		this.args = new ArrayList<String>(Arrays.asList(arguments));
		
		if (args.size() != 2) {
			return error.getError("Invalid Argument", "Expected 2 arguments");
		}
		
		if (args.get(0).equals("\\")){
			return error.getError("Invalid Directory", "Cannot move the root directory");
		}
		
		Cd traverse = new Cd();
		String[] currentPath = {getCurrentPath(filesys)};
		String[] pathFrom = {args.get(0)};
		String[] pathTo = {args.get(1)};
		int toRemove = -1;
		
		Node parentToMove;
		Node toMove;
		
		if (traverse.run(pathFrom, filesys)) {
			toMove = filesys.getCurrent();
			parentToMove = toMove.getParent();
			
			for (int i = 0; i < parentToMove.getList().size(); i++) {
				if (parentToMove.getList().get(i).equals(toMove)) {
					toRemove = i;
				}
			}

			traverse.run(currentPath, filesys);
		}else {
			return error.getError("Invalid Directory", pathFrom[0] + " does not exist!");
		}
		
		if (traverse.run(pathTo, filesys)) {
			filesys.addToDirectory(toMove);
		}else {
			return error.getError("Invalid Directory", pathTo[0] + " does not exist!");
		}
		
		filesys.assignCurrent(parentToMove);
		filesys.removeFromDirectory(toRemove);
		traverse.run(currentPath, filesys);
		
		return null;
	}
	

}
