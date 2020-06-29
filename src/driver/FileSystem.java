package driver;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class FileSystem {
	
	static FileSystem fileSys = null;
	static ArrayList<String> CommandLog =  new ArrayList<String>();
	static ArrayList<String> stack = new ArrayList<String>();

	Node root = new Node();	
	Node current = new Node();
	
	
	private FileSystem() {
		root.isDir = true;
		root.content = null;
		root.name = "C";
		root.isRoot = true;
		root.parent = null;
		current = root;
		
		//First dummy in Root
		Node users = new Node();
		users.isDir = true;
		users.content = null;
		users.name = "users";
		users.parent = root;
		
		//second dummy in Root
		Node pics = new Node();
		pics.isDir = true;
		pics.content = null;
		pics.name = "pics";
		pics.parent = root;
		
		//Picfile in pics
		Node picfile = new Node();
		picfile.isDir = false;
		picfile.content = "this is a picture";
		picfile.name = "picturefile.txt";
		picfile.parent = pics;
		
		pics.list.add(picfile);
		
		root.list.add(users);
		root.list.add(pics);
		
		// C
		    // Users
			// pics
				//picturefile.txt
	}
	
	public static FileSystem getFileSys() {
		if (fileSys == null) {
			fileSys = new FileSystem();
			return fileSys;
		} else {
			return fileSys;
		}
	}
	
	public Node getRoot() {
		return root;
	}
	
	public Node getCurrent() {
		return current;
	}
	
	public void assignCurrent(Node currentDirectory) {
		current = currentDirectory;
	}
	
	public void addToDirectory(Node newNode) {
		newNode.parent = current;
		current.list.add(newNode);
	}

}
