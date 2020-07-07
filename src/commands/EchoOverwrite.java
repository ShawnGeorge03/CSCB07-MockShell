package commands;

import data.Node;

public class EchoOverwrite extends Echo{
  
  public void execute(String fileContents, String fileName) {
    
    String[] path = fileName.split("/");
    
    if(path.length == 1) {
      if(isValidFileName(fileName)) {
        if(findFileGivenRelative(fileName) != null) {
          Node file = findFileGivenRelative(fileName);
          file.setContent(fileContents);
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
          Node parent = this.findInDirectory(path[path.length-2]);
          this.filesys.assignCurrent(parent);
          System.out.println(parent.getName());
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
