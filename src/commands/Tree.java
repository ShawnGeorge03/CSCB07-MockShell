package commands;

import data.FileSystemI;
import data.Node;
import errors.InvalidArgsProvidedException;

/**
 * Class Tree is responsible for displaying all directories in the filesystem (including files)
 */
public class Tree implements CommandI {
	
	/**
	 * Declaring instance variable for RedirectionManager
	 */
	RedirectionManager rManager;

	/**
	 * Constructor for Tree which initializes errorhandler object
	 */
	public Tree() {
		rManager = new RedirectionManager();
	}

	/**
	 * Override run method from command interface which checks if arguments are valid, then runs the program
	 * 
	 * @param fs the filesystem interface we are using to access the filesystem
	 * @param args the string array that contains the arguments the user has provided with the command
	 * @param fullinput the string that contains the entire line of input provided to jshell
	 * @param val the boolean we are using
	 * 
	 * @return any error messages there may be
	 */
	@Override
	public String run(FileSystemI fs, String[] arguments, String fullInput, boolean val) {
		//Seperates the parameters from everything else from the user input
		String[] args = rManager.setParams(fullInput);
		//Try catch to test validity of args
		try {
			checkArgs(fs, args, fullInput); 
		} catch (InvalidArgsProvidedException e) {
			return e.getLocalizedMessage();
		}
		String output = "";
		//Traverse file system and gather the desired tree format in the string output
		output = traverse(fs.getRoot(), 0, output);
		return output;
	}

	private String traverse(Node root, int depth, String output) {
		//Print tabs depending on the depth of the location in filesystem
		for (int i = 0; i < depth; i++) {
			output += "\t";
		}
		//Add name of node that we are currently at and print it
		output += root.getName();
		output += "\n";
		//System.out.println(output);
		for (int i = 0; i < root.getList().size(); i++) {
			//increment depth by 1 then recursively call the method to perform a DFS into
			//the last node and then decrement the depth back
			depth += 1;
			output = traverse(root.getList().get(i), depth, output);
			depth -= 1;
		}
		return output;
	}

	/**
	 * Overridden method checkArgs which ensures that no arguments have been called for tree, as it requires no arguments
	 * 
	 * @param fs the variable we are using to access the filesystem
	 * @param arguments the string array containing all the arguments provided alongside the command
	 * @param fullInput the string that contains the raw input provided to console in jshell
	 * 
	 * @return boolean true if there were no arguments provided
	 * 
	 * @throws InvalidArgsProvidedException the user provided invalid argument
	 */
	@Override
	public boolean checkArgs(FileSystemI fs, String[] arguments, String fullInput) throws InvalidArgsProvidedException {
		//Check if any arguments were provided
		if (arguments.length != 0) {
			throw new InvalidArgsProvidedException("Error : Multiple Parameters have been provided");
		}
		return true;
	}

}
