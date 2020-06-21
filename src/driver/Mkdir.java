package driver;

public class Mkdir extends DirectoryManager{
	
	public void MakeDirectory(){
		FileSystem filesys = null;
		filesys = FileSystem.getFileSys();
		
		Node current = filesys.getCurrent();
		
		while (current.root != null) {
			current = current.root;
		}
		
		// Node newDirectory = Node();
		// current.root = newDirectory;
	}
}
