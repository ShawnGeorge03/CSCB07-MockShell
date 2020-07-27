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
		int depth = 0;
		traverse(depth, fs, fs.getRoot());
		return null;
	}

	public void traverse(int depth, FileSystemI f, Node currDir) {
		if (currDir.getisDir()) {
			printDir(currDir, depth);
		}
		if (currDir.getList() != null) {
			for (int i = 0; i < currDir.getList().size(); i++) {
				traverse(depth + 1, f, currDir.getList().get(i));
			}
		}
	}

	public void printDir(Node dirToPrint, int depth) {
		System.out.println("\t");
		if (dirToPrint.getList() != null) {
			for (int i = 0; i < dirToPrint.getList().size(); i++) {
				System.out.println(dirToPrint.getList().get(i));
			}
		}
	}

//	public static void main(String[] args) {
//		FileSystem fs = getFileSys();
//		Tree t = new Tree();
//		t.run(fs, null, null, true);
//	}

}
