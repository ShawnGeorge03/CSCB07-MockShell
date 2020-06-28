package driver;

import java.util.ArrayList;

public class DirectoryManager {

	FileSystem filesys = null;
	ArrayList<String> path = new ArrayList<String>();

	public DirectoryManager() {
		filesys = FileSystem.getFileSys();
	}

	public String getCurrentPath() {
		String output = "";
		Node curr = filesys.getCurrent();

		if (curr.name.equals("C")) {
			return "C";
		}

		path.add(curr.name);
		while (curr.name != "C") {
			curr = curr.parent;
			path.add(curr.name);
		}

		int i = path.size();
		while (i > 0) {
			output = output.concat(path.get(i - 1) + "/");
			i--;
		}

		path.clear();

		return output.substring(0, output.length() - 1);
	}

	public int checkDirExists(ArrayList<Node> toCheck, String dir) {
		for (int i = 0; i < toCheck.size(); i++) {
			if ((toCheck.get(i)).name.equals(dir)) {
				return i;
			}
		}
		return -1;
	}

	public void makePathFromRoot(String path) {
		String[] pathArr = path.split("/");
		Node currNode = filesys.getRoot();
		for (int i = 0; i < pathArr.length; i++) {
			int indexOfDir = checkDirExists(currNode.list, pathArr[i]);
			if (indexOfDir != -1) {
				currNode = currNode.list.get(indexOfDir);
			} else {
				// Error msg (path not found)
			}
		}
		filesys.assignCurrent(currNode);
	}

	public void makeRelativePath(String path) {
		String[] pathArr = path.split("/");
		Node currNode = filesys.getCurrent();
		for (int i = 0; i < pathArr.length; i++) {
			int indexOfDir = checkDirExists(currNode.list, pathArr[i]);
			if (indexOfDir != -1) {
				if (currNode.list.get(indexOfDir).isDir) {
					currNode = currNode.list.get(indexOfDir);
				}
				else {
					//Error msg. suggestedd path is not a directory!
					return;
				}
			} else {
				System.out.println("Error. Directory not found");
			}
		}
		filesys.assignCurrent(currNode);
	}

}
