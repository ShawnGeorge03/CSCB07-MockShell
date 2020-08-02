package commands;

import java.io.FileWriter;
import java.io.IOException;
import data.*;
import errors.FileException;
import errors.InvalidArgsProvidedException;
import errors.InvalidRedirectionError;

/**
 * Class Save is responsible for saving the current filesystem to a json file on the users computer
 */
public class Save implements CommandI {

  private FileWriter writer;
  private RedirectionManager redirect;
  private String filePath;
  private String output;
  private String fileContent;

  public Save() {
    this.redirect = new RedirectionManager();
    this.output = null;
    this.fileContent = "";
  }

  @Override
	public boolean checkArgs(FileSystemI fs, String[] arguments, String fullInput) throws InvalidArgsProvidedException {
    //checks if user inputted a parameter or not
    if(arguments.length == 0){ 
      throw new InvalidArgsProvidedException("Error : No parameters provided");
    }
    //checks if user inputted more than one parameter
    else if(arguments.length > 1){  
      throw new InvalidArgsProvidedException("Error : Multiple Parameters have been provided : " + String.join(" ", arguments) + " Only one is required");
    }
    return true;
	}

  /*
   * Things to work on: - JavaDoc - Test cases
   */

  @Override
  public String run(FileSystemI filesys, String[] arguments, String fullInput, boolean val) {
    //Seperates the parameters from everything else from the user input
    String[] args = redirect.setParams(fullInput);
    try {
      //checks if user tries redirection
      redirect.isRedirectionableCommand(fullInput); 
      //checks if the arguments are valid
      if(checkArgs(filesys, args, fullInput)){ 
        //formats the filepath to work (since windows requires \\ to be used in the path instead of / or \)
        filePath = formatArguments(args);
        try {
          //checks for valid filename
          validateFileName(filesys, fullInput);
          writer = new FileWriter(filePath);
          //Adds the node information of every node to the file
          writer.write("NODES\n{\n");
          storeNodeInformation(writer, filesys);
          writer.write("}");
          fileContent += "\n";
          //Adds the filesystem structure to the file (similar to tree)
          writer.write("\n\nFILESYSTEM\n{\n");
          storeFileSystem(writer, filesys);
          writer.write("}");
          fileContent += "\n";
          //Adds the command log to the file
          writer.write("\n\nCOMMAND LOG\n{\n");
          storeCommandHistoryToFile(writer, filesys);
          writer.write("}");
          writer.close();
        } 
        // Invalid arguments were provided
        catch(InvalidArgsProvidedException e1){
          return e1.getLocalizedMessage();
        // Could not find file/Invalid Path was provided
        }catch (IOException e1) {
          output = "Error: Invalid Path : " + args[0];
        }
      }
    } 
    // Invalid arguments were provided
    catch (InvalidArgsProvidedException e) {
      return e.getLocalizedMessage();
    }
    return output;
  }

  /**
   * Method that checks if the file name that the user inputted is a valid file name. If the file name
   * contains illegal characters then this method returns false. If the file name does not contain any
   * illegal characters then it returns true.
   * 
   * @param fileName  String that stores the file name that the user inputted
   * @return boolean false if file name contains illegal characters, otherwise returns true
   */
  public boolean isValidName(String fileName) {
    String[] invalidChars = {"/", "\\s+", "!", "@", "#", "$", "%", "^",
        "&", "*", "(", ")", "{", "}", "~", "|", "<", ">", "?", "'", "[", "]"};
    for (int i = 0; i < invalidChars.length; i++) {
      if (fileName.contains(invalidChars[i])) {
        return false;
      }
    }
    return true;
  }

  public String getFileContent(FileSystemI filesys, String[] args, String fullInput, boolean val) {
    try {
      //Checks if redirection is allowed 
      redirect.isRedirectionableCommand(fullInput);
    } 
    //Redirection is not allowed so throw an error
    catch (InvalidRedirectionError e) {
      return e.getLocalizedMessage();
    }
    //Output hold the result of the command execution
    output = run(filesys, args, fullInput, false);
    //If there was an error then return the error
    if(output != null){
      if(output.startsWith("Error:") || output.startsWith("Error :")) return output;
    }
    //return the fileContents (only the node information is being checked)
    return fileContent.trim();
  }
  
  private void validateFileName(FileSystemI filesys, String fullInput) throws FileException{
    //Checks of the filename is valid
    if(!checkFileName(filePath, filesys)) {
      throw new FileException("Error: Invalid File : " + fullInput);
    }
    //If the fileName contains a file type 
    if(filePath.contains(".")){
      //Checks if json file
      if(!filePath.substring(filePath.length()-5, filePath.length()).equals(".json")) {
        throw new FileException("Error: Invalid File : " + fullInput);
      }
    }
    //there is no .json at the end so append .json to the fileName
    else filePath += ".json";
  }

  private boolean checkFileName(String filePath, FileSystemI filesys){
    String fileName = "";
    //If filename is given as absolute path then grab the fileName
    if(filePath.contains("\\")) filePath.substring(filePath.lastIndexOf("\\"), filePath.length());
    else fileName = filePath;
    //Check if filename is valid
    if(isValidName(fileName)) return true;
    return false;
  }

  private String formatArguments(String[] args) {
    //replaces / with \\
    if(args[0].contains("/")) return args[0].replace("/", "\\\\");
    //replaces \ with \\
    if(args[0].contains("\\")) return args[0].replace("\\", "\\\\");
    return args[0];
  }
  
  private void storeFileSystem(FileWriter writer, FileSystemI filesys) {
    Node root = filesys.getRoot();
    //Traverses the filesystem using Depth First Search 
    traverseFileSystem(root, writer, 1, 1);
  }
  
  private void storeNodeInformation(FileWriter writer, FileSystemI filesys){
    Node root = filesys.getRoot();
    //Traverses the filesystem using Depth First Search
    traverseFileSystem(root, writer, 1, 2);
  }
  
  private void traverseFileSystem(Node root, FileWriter writer, int depth, int mode){
    //If we want to add the node to our filesystem section of the file
    if(mode == 1) addNodeNameToFile(root, writer, depth);
    //If we want to add the individual node information to out nodes section of the file
    if(mode == 2) addNodeInformationToFile(root, writer, depth);
    //for loop to iterate through the arraylist of other nodes
    for(int i = 0; i < root.getList().size(); i++) {
      //needs this to add a depth when going deeper into filesystem
      depth += 1;
      //traverse filesystem again
      traverseFileSystem(root.getList().get(i), writer, depth, mode);
      //when we return from the previous call, the depth is one less
      depth -= 1;
    }
  }
  
  private void addNodeNameToFile(Node current, FileWriter writer, int depth) {
    try {
      String result = "";
      //for loop that adds a tab representing the depth
      for(int i = 0; i < depth; i++) {
        result += "\t";
      }
      //adds the node to the filesystem section of the file
      fileContent += result + "\"" + current.getName() + "\"\n";
      writer.write(result + "\"" + current.getName() + "\"\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void addNodeInformationToFile(Node current, FileWriter writer, int depth) {
    try {
      //Adds the node information to the nodes section of the file
      fileContent += "\t\"name\" : \"" + current.getName() + "\"\n";
      fileContent += "\t\"isDir\" : \"" + current.getisDir() + "\"\n";
      writer.write("\t\"name\" : \"" + current.getName() + "\"\n");
      writer.write("\t\"isDir\" : \"" + current.getisDir() + "\"\n");
      //If the parent of the node is not null then we add the parent name
      if(current.getParent() != null){
        fileContent += "\t\"parent\" : \"" + current.getParent().getName() + "\"\n";
        writer.write("\t\"parent\" : \"" + current.getParent().getName() + "\"\n");
      }
      //Else set the parent to be null (only case this occurs is the root node)
      else{
        fileContent += "\t\"parent\" : \"null\"\n";
        writer.write("\t\"parent\" : \"null\"\n");
      }
      writer.write("\t\"content\" : \"" + getContentOfNode(current) + "\"\n\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private String getContentOfNode(Node current){
    String finalOutput = null;
    //grabs current content of node
    String content = current.getContent();
    //replaces all newline characters with "\n" so it would fit in one line in the json file
    if(content != null)finalOutput = content.replaceAll("\n", "\\\\n");
    return finalOutput;
  }

  private void storeCommandHistoryToFile(FileWriter writer, FileSystemI filesys) {
    //loops through the command log of the filesystem
    for(int i = 0; i < filesys.getCommandLog().size(); i++) {
      try {
        //adds the command to the command log section of the file
        fileContent += "\t\"" + filesys.getCommandLog().get(i) + "\"\n";
        writer.write("\t\"" + filesys.getCommandLog().get(i) + "\"\n");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
}
