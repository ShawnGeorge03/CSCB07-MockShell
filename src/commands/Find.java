package commands;

import java.util.ArrayList;
import java.util.Arrays;

import data.FileSystemI;
import data.Node;
import errors.DirectoryException;
import errors.InvalidArgsProvidedException;

public class Find extends DirectoryManager implements CommandI {

	String output = "";
	RedirectionManager rManager;

	public Find() {
		rManager = new RedirectionManager();
	}

	@Override
	public String run(FileSystemI filesys, String fullInput, boolean val) {
		//Seperates the parameters from everything else from the user input
		String[] args = rManager.setParams(fullInput);
		
		ArrayList<String> paths = new ArrayList<String>();
		ArrayList<String> arguments = new ArrayList<String>(Arrays.asList(args));
		try {
			if (checkArgs(filesys, args, fullInput)){
				for (int i = 0; i < args.length; i++) {
					paths.add(args[i]);
					if (i < args.length - 1) {
						if (args[i + 1].equals("-type")) {
							break;
						}
					}
				}
				for (String x : paths) {
					arguments.remove(x);
				}
				if (checkArgs(filesys, args, fullInput)) {
					for (String x : paths) {
						String[] pathArr = { x };
						output = (checkList(filesys, pathArr, arguments.get(3), arguments.get(1)));
					}
				}
			}
		} catch (InvalidArgsProvidedException e) {
			return e.getLocalizedMessage();
		}
		
		return output;
	}

	@Override
	public boolean checkArgs(FileSystemI fs, String[] arguments, String fullInput) throws InvalidArgsProvidedException {
		ArrayList<String> args = new ArrayList<String>(Arrays.asList(arguments));

		if (args.size() == 0 || args.size() < 4) {
			throw new InvalidArgsProvidedException("Error: Invalid Argument : Missing Arguments");
		} else if (args.size() > 4) {
			throw new InvalidArgsProvidedException("Error : Multiple Parameters have been provided");
		} else if (!(args.get(0).equals("-type"))) {
			throw new InvalidArgsProvidedException("Error: Invalid Argument : " + args.get(0));
		} else if (!(args.get(1).equals("f") || args.get(1).equals("d"))) {
			throw new InvalidArgsProvidedException("Error: Invalid Argument : " + args.get(1));
		} else if (!(args.get(2).equals("-name"))) {
			throw new InvalidArgsProvidedException("Error: Invalid Argument : " + args.get(2));
		} else if (args.get(3).equals(null) 
					|| (args.get(3).charAt(0)) != ('"') 
					|| (args.get(3).charAt((args.get(3).length() - 1)) != ('"'))) {
			throw new InvalidArgsProvidedException("Error: Invalid Argument : " + args.get(3));
		}
		return true;
	}
	
	private String checkList(FileSystemI filesys, String[] path, String expression, String type) throws DirectoryException{
		expression = expression.substring(1, expression.length() - 1);
		String[] currPath = { filesys.getCurrentPath() };
		Cd newPath = new Cd();
		if (newPath.run(path, filesys)) {
			output = recursiveDirSearch(filesys, path, expression, type, newPath, filesys.getCurrent(),
					filesys.getCurrentPath(), output);
		} else {
			throw new DirectoryException("Error: Directory Not Found : " + path[0]);
		}
		newPath.run(currPath, filesys);
		return output;
	}

	public String recursiveDirSearch(FileSystemI filesys, String[] path, String expression, String type, Cd newPath,
			Node currNode, String currPath, String output) {

		output = printMatches(filesys, expression, type, output);
		for (int i = 0; i < currNode.getList().size(); i++) {
			String[] tempPath = { currNode.getList().get(i).getName() };
			if (newPath.run(tempPath, filesys)) {
				recursiveDirSearch(filesys, path, expression, type, newPath, filesys.getCurrent(), currPath, output);
			}
		}
		String[] temp = { currPath };
		newPath.run(temp, filesys);
		return output;
	}

	private String printMatches(FileSystemI filesys, String expression, String type, String output) {
		ArrayList<Node> toCheck = filesys.getCurrent().getList();
		for (int i = 0; i < toCheck.size(); i++) {
			if (type.equals("d")) {
				if (toCheck.get(i).getisDir() && toCheck.get(i).getName().equals(expression)) {
					output = output.concat(filesys.getCurrentPath() + " : " + toCheck.get(i).getName());
					output = output.concat("\n");
				}
			} else if (type.equals("f")) {
				if (!toCheck.get(i).getisDir() && toCheck.get(i).getName().equals(expression)) {
					output = output.concat(filesys.getCurrentPath() + " : " + toCheck.get(i).getName());
					output = output.concat("\n");
				}
			}
		}
		return output;
	}
}
