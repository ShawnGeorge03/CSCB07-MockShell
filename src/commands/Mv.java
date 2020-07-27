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

		String[] currentPath = {filesys.getCurrentPath()};
		String[] pathFrom = {""};
		String fileName;
		if (args.get(0).contains("/")){
			pathFrom[0] = args.get(0).substring(0, args.get(0).lastIndexOf("/"));
			if (pathFrom[0].equals("")){
				pathFrom[0] = "/";
			}
			if (args.get(0).lastIndexOf("/") == 0){
				fileName = args.get(0).substring(1, args.get(0).length());
			}else{
				fileName = args.get(0).substring(args.get(0).lastIndexOf("/") + 1, args.get(0).length());
			}
		}else{
			pathFrom[0] = "/";
			fileName = args.get(0);
		}
		
		String[] pathTo = {args.get(1)};
		Node parentToMove;
		Node toMove = null;
		int toRemove = -1;

		if (traverse.run(pathFrom, filesys)) {
			parentToMove = filesys.getCurrent();

			for (int i = 0; i < parentToMove.getList().size(); i++) {
				if (parentToMove.getList().get(i).getName().equals(fileName)) {
					Node copy = parentToMove.getList().get(i);
					ArrayList<Node> copyContents = new ArrayList<Node>(copy.getList());
					toMove = new Node.Builder(copy.getisDir(), copy.getName())
						.setContent(copy.getContent())
						.setList(copyContents)
						.setParent(parentToMove)
						.setRoot(false).build();
					toRemove = i;
				}
			}

			traverse.run(currentPath, filesys);
		}else {
			return error.getError("Invalid Directory", pathFrom[0] + " does not exist!");
		}
		
		if (traverse.run(pathTo, filesys)) {
			if (filesys.checkRepeat(fileName)){
				filesys.addToDirectory(toMove);
				traverse.run(pathFrom, filesys);
				filesys.removeFromDirectory(toRemove);
				System.out.println("hi");
			}else{
				return error.getError("Same Directory", fileName + " already exists");
			}
			
		}else {
			return error.getError("Invalid Directory", pathTo[0] + " does not exist!");
		}
		
		traverse.run(currentPath, filesys);
		
		return null;
	}
	

}
