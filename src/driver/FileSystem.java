package driver;

public class FileSystem {
	
	static FileSystem fileSys = null;
	Node root;	
	Node current;
	
	
	private FileSystem() {
		root.isDir = true;
		root.content = null;
		root.name = "C";
		root.next = null;
		root.parent = null;
		root.root = null;
		
		current = root;
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
	
	public static FileSystem getFileSys() {
		if (fileSys == null) {
			fileSys = new FileSystem();
			return fileSys;
		} else {
			return fileSys;
		}
	}

}
