package commands;

import java.util.ArrayList;
import java.util.Arrays;

import data.FileSystemI;
import data.Node;

public class Cp extends DirectoryManager implements CommandI{
	
	/**
	 * Declare instance variable of ArrayList to hold all arguments
	 */
	ArrayList<String> args;
	/**
	 * Declare instance variable of ErrorHandler to handle error messages
	 */
	private ErrorHandler error;

	Cd traverse;

	String[] pathFrom = {""};
	String fileName;
	String[] pathTo = {""};
	String[] currentPath = {""};
	Node parentToMove;
	Node toMove = null;
	FileSystemI fs;
	String output;
	
	public Cp() {
		error = new ErrorHandler();
		traverse = new Cd();
		
	}
	
	public String run(FileSystemI filesys, String[] arguments, String actualInput, boolean val) {
		this.args = new ArrayList<String>(Arrays.asList(arguments));
		fs = filesys;
		pathTo[0] = args.get(1);
		output = checkArgs();
		if (output != null){
			return output;
		}

		currentPath[0] = fs.getCurrentPath();

		initPathandFile();

		output = copyFile();
		if (output != null){
			return output;
		}

		output = moveFile();
		if (output != null){
			return output;
		}
		
		traverse.run(currentPath, fs);
		
		return null;
	}

	public void initPathandFile(){
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
	}

	public String checkArgs(){
		if (args.size() != 2) {
			return error.getError("Invalid Argument", "Expected 2 arguments");
		}
		
		if (args.get(0).equals("/")){
			return error.getError("Invalid Directory", "Cannot move the root directory");
		}
		return null;
	}

	public String copyFile(){
		if (traverse.run(pathFrom, fs)) {
			pathFrom[0] = fs.getCurrentPath();
			parentToMove = fs.getCurrent();

			for (int i = 0; i < parentToMove.getList().size(); i++) {
				if (parentToMove.getList().get(i).getName().equals(fileName)) {
					Node copy = parentToMove.getList().get(i);
					ArrayList<Node> copyContents = new ArrayList<Node>(copy.getList());
					toMove = new Node.Builder(copy.getisDir(), copy.getName())
						.setContent(copy.getContent())
						.setList(copyContents)
						.setParent(parentToMove)
						.setRoot(false).build();
				}

				if (toMove == null){
					return error.getError("Directory Not Found", fileName + " does not exist in the path you specified!");
				}
			}

			traverse.run(currentPath, fs);
		}else {
			return error.getError("Invalid Directory", pathFrom[0] + " does not exist!");
		}
		return null;
	}

	public String moveFile(){
		if (traverse.run(pathTo, fs)) {
			if (fs.checkRepeat(fileName)){
				fs.addToDirectory(toMove);
			}else{
				return error.getError("Same Directory", fileName + " already exists");
			}
			
		}else {
			return error.getError("Invalid Directory", pathTo[0] + " does not exist!");
		}
		return null;
	}

}
