package driver;

public class Ls extends DirectoryManager{
	
	public void listDirectory() {
		FileSystem filesys = null;
		filesys = FileSystem.getFileSys();
		
		Node current = filesys.getCurrent();
		
		while (current.root != null) {
			System.out.println(current.root.name);
			current.root = current.root.next;
		}
	}
}
