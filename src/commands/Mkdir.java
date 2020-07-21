// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: patelt26
// UT Student #: 1005904103
// Author: Shawn Santhoshgeorge
//
// Student2:
// UTORID user_name: shaiskan
// UT Student #: 1006243940
// Author: Keshavaa Shaiskandan
//
// Student3:
// UTORID user_name: patelt26
// UT Student #: 1005904103
// Author: Tirth Patel
//
// Student4:
// UTORID user_name: pate1101
// UT Student #: 1006315765
// Author: Abhay Patel
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package commands;

import java.util.ArrayList;
import java.util.Arrays;

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
		String output = "";
		//Checks for Valid arguments
			//Checks if a path was given or if the name was just iven
		for (int i = 0; i < args.size(); i++) {
			if (checkPath(i)) {
				String[] currentPath = { getCurrentPath() };
				String[] newArgs = { args.get(i).substring(0, args.get(i).lastIndexOf('/')) };
				
				//Checks if directory name is valid
				if (!isValidDirectoryName(args.get(i).substring(args.get(i).lastIndexOf('/') + 1))) {
					output += error.getError("Invalid Directory",
							args.get(i).substring(args.get(i).lastIndexOf('/') + 1) + " is not a valid directory name");
					continue;
				}
				
				//Cd's into path given, checks if filename is valid for that directory, and creates it
				Cd newpath = new Cd();
				if (newpath.run(newArgs)) {
					Node newNode = getDirNode(i);
					for (int j = 0; j < FileSystem.getFileSys().getCurrent().getList().size(); j++) {
						String currentNodeName = FileSystem.getFileSys().getCurrent().getList().get(j).getName();
						if (FileSystem.getFileSys().getCurrent().getList().get(j).getName().equals(newNode.getName())) {
							Cd goBack = new Cd();
							
							goBack.run(currentPath);
							output += error.getError("Same Directory", newArgs[0] + " already exists");
							continue;
						}
					}
					
					FileSystem.getFileSys().addToDirectory(newNode);
				} else {
					output += error.getError("Invalid Directory", newArgs[0] + " is not a valid directory");
				}
				newpath.run(currentPath);
			} else {
				output += mkDirWithinCurrent(i);
			}
		}
		if (output.equals("")) {
			return null;
		}
		return output;
	}


	/**
	 * Returns a boolean if the argument is a relative or absolute path or not
	 * 
	 * @return Boolean  A boolean value indicating the above
	 */
	private boolean checkPath(int i) {
		return args.get(i).contains("/");
	}
	
	/**
	 * Makes a Node and adds it to the current working directory
	 * 
	 * @return String  A string if there is an error in adding the node, else null
	 */
	private String mkDirWithinCurrent(int i) {
		if (!isValidDirectoryName(args.get(i))) {
			return error.getError("Invalid Directory", args.get(i) + " is not a valid directory name");
		}

		Node newNode = new Node();
		newNode.setContent(null);
		newNode.setDir(true);
		newNode.setName(args.get(i));

		for (int j = 0; j < FileSystem.getFileSys().getCurrent().getList().size(); j++) {
			if (FileSystem.getFileSys().getCurrent().getList().get(j).getName().equals(newNode.getName())) {
				return error.getError("Same Directory", newNode.getName() + " already exists");
			}
		}

		filesys.addToDirectory(newNode);
		return "";
	}
	
	/**
	 * Creates an instance of a Node to be returned to be added into the directory
	 * 
	 * @return Node  The new node to be added
	 */
	private Node getDirNode(int i) {
		Node newNode = new Node();
		newNode.setContent(null);
		newNode.setDir(true);
		newNode.setName(args.get(i).substring(args.get(i).lastIndexOf('/') + 1));
		return newNode;
	}

}
