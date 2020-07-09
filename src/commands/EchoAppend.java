// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: patelt26
// UT Student #: 1005904103
// Author: Shawn Santhoshgeorge
//
// Student2:
// UTORID user_name: shaiskan
// UT Student #: 1006243940
// Author: Keshavaa Shaiskandan
//
// Student3:
// UTORID user_name: patelt26
// UT Student #: 1005904103
// Author: Tirth Patel
//
// Student4:
// UTORID user_name: pate1101
// UT Student #: 1006315765
// Author: Abhay Patel
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package commands;

import data.Node;

/**
 * Class EchoAppend appends data onto the contents of the requested file
 */
public class EchoAppend extends Echo {

  /**
   * Takes the new content and appends it to the contents of the file
   * 
   * @param fileContents  the contents the user wishes to append
   * @param fileName  the name of requested file
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
        file.setContent(file.getContent() + "\n" + fileContents);
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
    // Check if fileName is valid
    if (isValidFileName(givenPath[givenPath.length - 1])) {
      // If already exists
      if (findFileGivenAbsolute(fileName) != null) {
        Node file = findFileGivenAbsolute(fileName);
        file.setContent(file.getContent() + "\n" + fileContents);
      } else {
        // Create new file and add to the FileSystem
        Node currentNode = this.filesys.getCurrent();
        String desiredPath = fileName;
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
