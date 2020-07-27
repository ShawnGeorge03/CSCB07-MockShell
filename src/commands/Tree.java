package commands;

import data.FileSystemI;
import data.Node;
import java.util.ArrayList;
import java.util.Arrays;

public class Tree implements CommandI {

	ErrorHandler e;

	public Tree() {
		e = new ErrorHandler();
	}

	@Override
	public String run(FileSystemI fs, String[] args, String fullInput, boolean val) {
		int depth = 1;
		System.out.println(fs.getRoot().getName());
		traverse(depth, fs, fs.getRoot());
		System.out.println("");
		return null;
	}

	public void traverse(int depth, FileSystemI f, Node currDir) {
		if (currDir.getList() != null) {
			if (currDir.getisDir()) {
				printDir(currDir, depth, f);
			}

			for (int i = 0; i < currDir.getList().size(); i++) {
				traverse(depth + 1, f, currDir.getList().get(i));
			}
		}
	}

	public void printDir(Node dirToPrint, int depth, FileSystemI f) {
		if (dirToPrint.getList() != null) {

			for (int i = 0; i < dirToPrint.getList().size(); i++) {
				for (int j = 0; j < depth; j++) {
					System.out.print(" "); //change to \t later!
				}
				System.out.println(dirToPrint.getList().get(i).getName());

			}
		}
	}

//	public static void main(String[] args) {
//		FileSystem fs = getFileSys();
//		Tree t = new Tree();
//		t.run(fs, null, null, true);
//	}

}
