package commands;

import java.util.ArrayList;
import java.util.Arrays;

import data.FileSystemI;
import data.Node;
import errors.DirectoryException;
import errors.InvalidArgsProvidedException;
import errors.InvalidRedirectionError;

public class Cp extends DirectoryManager implements CommandI {

	/**
	 * Declare instance variable of ArrayList to hold all arguments
	 */
	ArrayList<String> args;

	private RedirectionManager rManager;

	private Cd traverse;

	String[] pathFrom = { "" };
	String fileName;
	String[] pathTo = { "" };
	String[] currentPath = { "" };
	Node parentToMove;
	Node toMove = null;
	String output;

	public Cp() {
		rManager = new RedirectionManager();
		traverse = new Cd();
	}

	public String run(FileSystemI filesys, String[] arguments, String actualInput, boolean val) {
		this.args = new ArrayList<String>(Arrays.asList(arguments));
		try {
			rManager.isRedirectionableCommand(filesys, actualInput);
			checkArgs(filesys, actualInput);
		} catch (InvalidRedirectionError | DirectoryException | InvalidArgsProvidedException e) {
			return e.getLocalizedMessage();
		}

		pathTo[0] = args.get(1);
		currentPath[0] = filesys.getCurrentPath();
		initPathandFile();

		try {
			output = copyFile(filesys);
		} catch (DirectoryException | InvalidArgsProvidedException e) {
			return e.getLocalizedMessage();
		}

		try {
			output = moveFile(filesys);
		} catch (DirectoryException | InvalidArgsProvidedException e) {
			return e.getLocalizedMessage();
		}

		traverse.run(currentPath, filesys);

		return null;
	}

	public void initPathandFile() {
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
			pathFrom[0] = "/";
			fileName = args.get(0);
		}
	}

	public boolean checkArgs(FileSystemI filesys, String fullInput)
			throws InvalidArgsProvidedException, DirectoryException {
		if (args.size() != 2)
			throw new InvalidArgsProvidedException("Error: Invalid Argument : Expected 2 arguments");

		if (args.get(0).equals("/"))
			throw new DirectoryException("Error: Invalid Directory : Cannot move the root directory");

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

			if (toMove == null)
				throw new DirectoryException(
						"Error: Directory Not Found : " + fileName + " does not exist in the path you specified!");

			traverse.run(currentPath, fs);
		} else {
			throw new InvalidArgsProvidedException("Error: Invalid Directory : " + pathFrom[0] + " does not exist!");
		}
		return null;
	}

	public String moveFile(FileSystemI fs) throws InvalidArgsProvidedException, DirectoryException {
		if (traverse.run(pathTo, fs)) {
			if (fs.checkRepeat(fileName)) {
				fs.addToDirectory(toMove);
			} else {
				throw new DirectoryException(
						"Error: Same Directory with that name already exists! : " + fileName + " already exists");
			}

		} else {
			throw new InvalidArgsProvidedException("Error: Invalid Directory : " + pathTo[0] + " does not exist!");
		}
		return null;
	}

}
