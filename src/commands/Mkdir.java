package commands;

import java.util.ArrayList;
import java.util.Arrays;
import commands.DirectoryManager;
import data.FileSystem;
import data.Node;

/**
 * Class Mkdir handles making directories anywhere in the filesystem
 */
public class Mkdir extends DirectoryManager implements CommandI {
	/**
	 * Declare instance variable of ArrayList to hold all arguments
	 */
	ArrayList<String> args;
	/**
	 * Declare instance variable of ErrorHandler to handle error messages
	 */
	private ErrorHandler error;

	/**
	 * Constructor of Mkdir to initialize error
	 */
	public Mkdir() {
		this.error = new ErrorHandler();
	}

	/**
	 * Generic run method to call on method that does the work of creating
	 * directories
	 * 
	 * @param args  the string array of all arguments
	 * @param fullInput  the string of the entire raw input provided by user in
	 *                   JShell
	 * @return String  null always
	 */
	public String run(String[] args, String fullInput, boolean val) {
		String output = MakeDirectory(args);
		return output;
	}

	/**
	 * Makes directories at locations in filesystem based on the path given
	 * 
	 * @param arguments  The string array of all arguments provided
	 * @return String  An error message, else null
	 */
	public String MakeDirectory(String[] arguments) {
		this.args = new ArrayList<String>(Arrays.asList(arguments));
		if (checkValidArgs()) {
			if (checkPath()) {
				String[] currentPath = { getCurrentPath() };
				String[] newArgs = { args.get(0).substring(0, args.get(0).lastIndexOf('/')) };

				if (!isValidDirectoryName(args.get(0).substring(args.get(0).lastIndexOf('/') + 1))) {
					return error.getError("Invalid Directory",
							args.get(0).substring(args.get(0).lastIndexOf('/') + 1) + " is not a valid directory name");
				}

				Cd newpath = new Cd();
				if (newpath.run(newArgs)) {
					Node newNode = getDirNode();

					if (checkForRepitition(newNode, currentPath, newArgs) != null) {
						return checkForRepitition(newNode, currentPath, newArgs);
					}

					filesys.addToDirectory(newNode);
				} else return error.getError("Invalid Directory", newArgs[0] + " is not a valid directory");

				Cd goBack = new Cd();
				goBack.run(currentPath);
				return null;
			} else return mkDirWithinCurrent();
		} else return error.getError("Invalid Argument", "Expecting 1 Argument only");
	}

	/**
	 * Checks if and only if one argument was provided
	 * 
	 * @return Boolean  A boolean value indicating the above
	 */
	private boolean checkValidArgs() {
		return args.size() == 1;
	}

	/**
	 * Returns a boolean if the argument is a relative or absolute path or not
	 * 
	 * @return Boolean  A boolean value indicating the above
	 */
	private boolean checkPath() {
		return args.get(0).contains("/");
	}
	
	/**
	 * Makes a Node and adds it to the current working directory
	 * 
	 * @return String  A string if there is an error in adding the node, else null
	 */
	private String mkDirWithinCurrent() {
		if (!isValidDirectoryName(args.get(0))) {
			return error.getError("Invalid Directory", args.get(0) + " is not a valid directory name");
		}

		Node newNode = new Node();
		newNode.setContent(null);
		newNode.setDir(true);
		newNode.setName(args.get(0));

		for (int i = 0; i < filesys.getCurrent().getList().size(); i++) {
			if (filesys.getCurrent().getList().get(i).getName().equals(newNode.getName())) {
				return error.getError("Same Directory", newNode.getName() + " already exists");
			}
		}

		filesys.addToDirectory(newNode);
		return null;
	}
	
	/**
	 * Creates an instance of a Node to be returned to be added into the directory
	 * 
	 * @return Node  The new node to be added
	 */
	private Node getDirNode() {
		Node newNode = new Node();
		newNode.setContent(null);
		newNode.setDir(true);
		newNode.setName(args.get(0).substring(args.get(0).lastIndexOf('/') + 1));
		return newNode;
	}
	/**
	 * Checks if a soon to be added node has the same name as an already existing directory 
	 * node within the current working directory
	 * 
	 * @param newNode  Node to be added
	 * @param currentPath  Current working directory
	 * @param newArgs  Argument provided
	 * @return String  A string if only there is an error, else null
	 */
	private String checkForRepitition(Node newNode, String[] currentPath, String[] newArgs) {
		for (int i = 0; i < filesys.getCurrent().getList().size(); i++) {
			if (filesys.getCurrent().getList().get(i).getName().equals(newNode.getName())) {
				Cd goBack = new Cd();
				goBack.run(currentPath);
				return error.getError("Same Directory", newArgs[0] + " already exists");
			}
		}
		return null;
	}

}
