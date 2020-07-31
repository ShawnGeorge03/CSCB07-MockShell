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

import data.FileSystemI;
import data.Node;
import errors.DirectoryException;
import errors.InvalidArgsProvidedException;

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

	private RedirectionManager rManager;

	private Cd traverse;

	//String fullinput;

	/**
	 * Constructor of Mkdir to initialize error
	 */
	public Mkdir() {
		this.error = new ErrorHandler();
		this.rManager = new RedirectionManager();
		traverse = new Cd();
	}

	/**
	 * Generic run method to call on method that does the work of creating
	 * directories
	 * 
	 * @param args      the string array of all arguments
	 * @param fullInput the string of the entire raw input provided by user in
	 *                  JShell
	 * @return String null always
	 */
	public String run(FileSystemI filesys, String[] args, String fullInput, boolean val) {
		try {
			rManager.isRedirectionableCommand(filesys, fullInput);
			return MakeDirectory(args, filesys, fullInput);
		} catch (InvalidArgsProvidedException e) {
			return e.getLocalizedMessage();
		}
	}

	

	/**
	 * Makes directories at locations in filesystem based on the path given
	 * 
	 * @param arguments  The string array of all arguments provided
	 * @return String  An error message, else null
	 */
	public String MakeDirectory(String[] arguments, FileSystemI filesys, String fullinput) throws InvalidArgsProvidedException, DirectoryException{
		
		this.args = new ArrayList<String>(Arrays.asList(arguments));
		checkArgs(filesys, arguments, fullinput);
		
		if (args.size() == 0) {
			return error.getError("Invalid Argument", "Expected at least 1 argument");
		}
		
		String output = "";
		//Checks for Valid arguments
		for (int i = 0; i < args.size(); i++) {
			if (checkPath(i)) {
				String[] currentPath = {filesys.getCurrentPath()};
				String[] newArgs = { args.get(i).substring(0, args.get(i).lastIndexOf('/')) };
				if (newArgs[0].equals("")){
					newArgs[0] = "/";
				}
				
				//Checks if directory name is valid
				if (!filesys.isValidName(args.get(i).substring(args.get(i).lastIndexOf('/') + 1))) {
					output += error.getError("Invalid Directory",
							args.get(i).substring(args.get(i).lastIndexOf('/') + 1) + " is not a valid directory name");
					continue;
				}
				
				//Cd's into path given, checks if filename is valid for that directory, and creates it
				Cd newpath = new Cd();
				if (newpath.run(newArgs, filesys)) {
					Node newNode = getDirNode(i);
					boolean isValidDir = true;
					for (int j = 0; j < filesys.getCurrent().getList().size(); j++) {
						if (filesys.getCurrent().getList().get(j).getName().equals(newNode.getName())) {
							Cd goBack = new Cd();
							
							goBack.run(currentPath, filesys);
							output += error.getError("Same Directory", newNode.getName() + " already exists");
							isValidDir = false;
							continue;
						}
					}
					
					if (!isValidDir) {
						continue;
					}
					
					filesys.addToDirectory(newNode);
				} else {
					output += error.getError("Invalid Directory", newArgs[0] + " is not a valid directory");
					
				}
				newpath.run(currentPath, filesys);
			} else {
				output += mkDirWithinCurrent(i, filesys);
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
	private String mkDirWithinCurrent(int i, FileSystemI filesys) {
		if (!filesys.isValidName(args.get(i))) {
			return error.getError("Invalid Directory", args.get(i) + " is not a valid directory name");
		}

		Node newNode = new Node.Builder(true, args.get(i)).build();

		for (int j = 0; j < filesys.getCurrent().getList().size(); j++) {
			if (filesys.getCurrent().getList().get(j).getName().equals(newNode.getName())) {
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
		return new Node.Builder(true, args.get(i).substring(args.get(i).lastIndexOf('/') + 1)).build();
	}

	@Override
	public boolean checkArgs(FileSystemI fs, String[] arguments, String fullInput) throws InvalidArgsProvidedException {	
		for (int i = 0; i < args.size(); i++){
			String[] newArgs = {args.get(i).substring(0, args.get(i).lastIndexOf('/')) };
			if (newArgs[0].equals("")){
				newArgs[0] = "/";
			}
			String fileName = args.get(i).substring(args.get(i).lastIndexOf('/') + 1);
			if (!fs.isValidName(fileName)){
				error.getError("Invalid Directory", fileName + " is not a valid directory name");
			}
			if (!traverse.run(newArgs, fs)){
				error.getError("Invalid Directory", newArgs[0] + " is not a valid directory");
			}
		}
		return false;
	}

}
