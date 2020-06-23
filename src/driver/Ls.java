package driver;

public class Ls extends DirectoryManager{
	
	public void listDirectory() {
		FileSystem filesys = null;
		filesys = FileSystem.getFileSys();
		
		Node currentDir = filesys.getCurrent();
		
		for (int i = 0; i < currentDir.list.size(); i++) {
			System.out.println(currentDir.list.get(i).name);
		}
	}
}
