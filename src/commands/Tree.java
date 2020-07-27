package commands;

import data.FileSystemI;
import data.Node;

public class Tree implements CommandI {

	ErrorHandler e;

	public Tree() {
		e = new ErrorHandler();
	}

	@Override
	public String run(FileSystemI fs, String[] args, String fullInput, boolean val) {
		if (!isValid(args)) {
			return e.getError("Multiple parameters provided", "");
		}
		traverse(fs.getRoot(), 0);
		System.out.println("");
		return null;
	}
	
	public boolean isValid(String[] args) {
		if (args.length != 0) {
			return false;
		}
		return true;
	}

	private void traverse(Node root, int depth) {
		String output = "";
		for (int i = 0; i < depth; i++) {
			output += "\t";
		}
		output += root.getName();
		System.out.println(output);
		for (int i = 0; i < root.getList().size(); i++) {
			depth += 1;
			traverse(root.getList().get(i), depth);
			depth -= 1;
		}
	}

}
