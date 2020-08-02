package commands;

import java.util.ArrayList;
import java.util.Arrays;

import data.FileSystemI;
import data.Node;
import errors.DirectoryException;
import errors.InvalidArgsProvidedException;

public class Cp extends DirectoryManager implements CommandI {

	/**
	 * Declare instance variable of ArrayList to hold all arguments
	 */
	ArrayList<String> args;

	private RedirectionManager rManager;

	private Cd traverse;

	String[] pathFrom = { "" };
	String[] pathTo = { "" };
	String[] currentPath = { "" };
	String fileName;
	String newDir;
	Node parentToMove;
	Node toMove = null;
	String output;

	public Cp() {
		rManager = new RedirectionManager();
		traverse = new Cd();
	}

	public String run(FileSystemI filesys,  String[] args, String actualInput, boolean val) {
		//Seperates the parameters from everything else from the user input
		String[] arguments = rManager.setParams(actualInput);
		this.args = new ArrayList<String>(Arrays.asList(arguments));
		try {
			rManager.isRedirectionableCommand(actualInput);
			if(checkArgs(filesys, arguments, actualInput)){

				initPathandFile(filesys);	
				output = copyFile(filesys);
				output = moveFile(filesys);
			}						
		}catch(InvalidArgsProvidedException e){
			return e.getLocalizedMessage();
		}

		traverse.run(currentPath, filesys);

		return null;
	}

	public void initPathandFile(FileSystemI fs) throws DirectoryException{
		if (args.get(0).contains("/")) {
			pathFrom[0] = args.get(0).substring(0, args.get(0).lastIndexOf("/"));
			if (pathFrom[0].equals("")) {
				pathFrom[0] = "/";
			}
			if (args.get(0).lastIndexOf("/") == 0) {
				fileName = args.get(0).substring(1, args.get(0).length());
			} else {
				fileName = args.get(0).substring(args.get(0).lastIndexOf("/") + 1, args.get(0).length());
			}
		} else {
			pathFrom[0] = fs.getCurrentPath(); //CHANGE
			fileName = args.get(0);
		}
		if (args.get(1).contains("/")){
			pathTo[0] = args.get(1).substring(0, args.get(1).lastIndexOf("/"));
			if (pathTo[0].equals("")){
				pathTo[0] = "/";
			}
			if (args.get(1).lastIndexOf("/") == 0) {
				newDir = args.get(1).substring(1, args.get(1).length());
			} else {
				newDir = args.get(1).substring(args.get(1).lastIndexOf("/") + 1, args.get(1).length());
			}
		}else{
			pathTo[0] = fs.getCurrentPath();
			newDir = args.get(1);
		}
		//System.out.println(pathFrom[0] + "\n" + fileName + "\n" + pathTo[0] + "\n" + newDir);
		if (!fs.isValidName(newDir)){
			throw new DirectoryException("Error: Invalid Directory " + newDir + " is not a valid directory name");
		}
		currentPath[0] = fs.getCurrentPath();
		
	}

	public boolean checkArgs(FileSystemI filesys, String[] arguments, String fullInput)
			throws InvalidArgsProvidedException, DirectoryException {
		if (args.size() != 2)
			throw new InvalidArgsProvidedException("Error: Invalid Argument : Expected 2 arguments");

		if (args.get(0).equals("/"))
			throw new DirectoryException("Error: Invalid Directory : Cannot copy the root directory");

		return true;
	}

	public String copyFile(FileSystemI fs) throws InvalidArgsProvidedException, DirectoryException {
		if (traverse.run(pathFrom, fs)) {
			pathFrom[0] = fs.getCurrentPath();
			parentToMove = fs.getCurrent();

			for (int i = 0; i < parentToMove.getList().size(); i++) {
				if (parentToMove.getList().get(i).getName().equals(fileName)) {
					Node copy = parentToMove.getList().get(i);
					ArrayList<Node> copyContents = new ArrayList<Node>(copy.getList());
					toMove = new Node.Builder(copy.getisDir(), copy.getName()).setContent(copy.getContent())
							.setList(copyContents).setParent(parentToMove).setRoot(false).build();
				}
			}
			traverse.run(currentPath, fs);

			if (toMove == null){
				throw new DirectoryException(
						"Error: Directory Not Found : " + fileName + " does not exist in the path you specified!");
			}
			
		} else {
			throw new InvalidArgsProvidedException("Error: Invalid Directory : " + pathFrom[0] + " does not exist!");
		}
		return null;
	}

	public String moveFile(FileSystemI fs) throws InvalidArgsProvidedException, DirectoryException {
		if (traverse.run(pathTo, fs)) {
			if (newDir.equals("")){
				fs.addToDirectory(toMove);
				traverse.run(currentPath, fs);
				return null;
			}
			parentToMove = fs.getCurrent();
			checkDirectory(fs);
			int toOverride = -1;
			for (int i = 0; i < parentToMove.getList().size(); i++){
				if (toMove.getName().equals(parentToMove.getList().get(i).getName())){
					toOverride = i;
					break;
				}
			}

			if (toOverride == -1){
				fs.addToDirectory(toMove);
			}else{
				fs.removeFromDirectory(toOverride);
				fs.addToDirectory(toMove);
			}
			traverse.run(currentPath, fs);
		} else {
			throw new InvalidArgsProvidedException("Error: Invalid Directory : " + pathTo[0] + " does not exist!");
		}
		return null;
	}

	public void checkDirectory(FileSystemI fs){
		for (int i = 0; i < parentToMove.getList().size(); i++) {
			if (parentToMove.getList().get(i).getName().equals(newDir)){
				parentToMove = parentToMove.getList().get(i);
				fs.assignCurrent(parentToMove);
				return;
			}
		}

		Node newDirectory = new Node.Builder(true, newDir).setParent(parentToMove).build();
		fs.addToDirectory(newDirectory);
		fs.assignCurrent(newDirectory);
		parentToMove = newDirectory;
	}

}
