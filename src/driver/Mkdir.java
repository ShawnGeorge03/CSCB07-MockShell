package driver;

public class Mkdir extends DirectoryManager{
	
	public void MakeDirectory(){
		FileSystem filesys = null;
		filesys = FileSystem.getFileSys();
		
		Node newNode = new Node();
		
		// filesys.addToDirectory(newNode);
	}
}
