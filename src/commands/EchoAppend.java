package commands;

import data.Node;

/**
 * Class EchoAppend appends data onto the contents of the requested file
 */
public class EchoAppend extends Echo{
  
  /**
   * Takes the new content and appends it to the contents of the file
   * 
   * @param fileContents  Contents the user wishes to append
   * @param fileName  Name of requested file
   */
  public void execute(String fileContents, String fileName) {
    
    String[] path = fileName.split("/");
    
    if(path.length == 1) {
      if(isValidFileName(fileName)) {
        if(findFileGivenRelative(fileName) != null) {
          Node file = findFileGivenRelative(fileName);
          file.setContent(file.getContent() + "\n" + fileContents);
        }
        else {
          Node newFile = new Node();
          newFile.setDir(false);
          newFile.setContent(fileContents);
          newFile.setName(fileName);
          newFile.setParent(filesys.getCurrent());
          filesys.addToDirectory(newFile);
        }
      }
      else System.out.println(this.getErrorHandler().getError("Invalid File", fileName));
    }
    
    else {
      if(isValidFileName(path[path.length-1])) {
        if(findFileGivenAbsolute(fileName) != null) {
          Node file = findFileGivenAbsolute(fileName);
          file.setContent(fileContents);
        }
        else {
          Node currentNode = this.filesys.getCurrent();
          String desiredPath = getCurrentPath() + fileName;
          desiredPath = desiredPath.substring(0, desiredPath.lastIndexOf("/"));
          Node parent = findFolderGivenAbsolute(desiredPath);
          this.filesys.assignCurrent(parent);
          Node newFile = new Node();
          newFile.setDir(false);
          newFile.setContent(fileContents);
          newFile.setName(path[path.length-1]);
          newFile.setParent(parent);
          filesys.addToDirectory(newFile);
          this.filesys.assignCurrent(currentNode);
        }
      }
      else System.out.println(this.getErrorHandler().getError("Invalid File", path[path.length-2]));
    }
    
  }
  
}
