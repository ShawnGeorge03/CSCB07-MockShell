package commands;

import data.Node;

/**
 * Class EchoOverwrite overwrites the contents of requested file with given string
 */
public class EchoOverwrite extends Echo {

  /**
   * Takes contents and overwrites it to the contents of the requested file
   * 
   * @param fileContents the contents that the user wants to overwrite with
   * @param fileName name of the file
   */
  public void execute(String fileContents, String fileName) {
    // Parses the user inputted path
    String[] givenPath = fileName.split("/");
    // If given only the file (relative path)
    if (givenPath.length == 1) {
      executeRelativePath(fileContents, fileName);
    }
    // If given absolute path
    else {
      executeAbsolutePath(fileContents, fileName, givenPath);
    }

  }

  private void executeRelativePath(String fileContents, String fileName) {
    // Check if valid fileName
    if (isValidFileName(fileName)) {
      // If already exists
      if (findFileGivenRelative(fileName) != null) {
        Node file = findFileGivenRelative(fileName);
        file.setContent(fileContents);
      } else {
        // Create new file and add to FileSystem
        Node newFile = new Node();
        newFile.setDir(false);
        newFile.setContent(fileContents);
        newFile.setName(fileName);
        newFile.setParent(filesys.getCurrent());
        filesys.addToDirectory(newFile);
      }
    } else
      System.out
          .println(this.getErrorHandler().getError("Invalid File", fileName));
  }

  private void executeAbsolutePath(String fileContents, String fileName,
      String[] givenPath) {
    //Check if fileName is valid
    if (isValidFileName(givenPath[givenPath.length - 1])) {
   // If already exists
      if (findFileGivenAbsolute(fileName) != null) {
        Node file = findFileGivenAbsolute(fileName);
        file.setContent(fileContents);
      } else {
        //Create new file and add to the FileSystem
        Node currentNode = this.filesys.getCurrent();
        String desiredPath = getCurrentPath() + fileName;
        desiredPath = desiredPath.substring(0, desiredPath.lastIndexOf("/"));
        Node parent = findFolderGivenAbsolute(desiredPath);
        this.filesys.assignCurrent(parent);
        Node newFile = new Node();
        newFile.setDir(false);
        newFile.setContent(fileContents);
        newFile.setName(givenPath[givenPath.length - 1]);
        newFile.setParent(parent);
        filesys.addToDirectory(newFile);
        this.filesys.assignCurrent(currentNode);
      }
    } else
      System.out.println(this.getErrorHandler().getError("Invalid File",
          givenPath[givenPath.length - 2]));
  }

}
