package commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import data.FileSystemI;
import data.Node;
import errors.InvalidArgsProvidedException;
import errors.InvalidRedirectionError;

public class Load implements CommandI{

  private FileReader fileReader;
  private BufferedReader reader;
  private String filePath;
  private String output;
  private String fileContents;

  /**
   * Declare instance variable of RedirectionManager to handle redirection to file
   */
  private RedirectionManager redirect;
  
  public Load(){
    // Initializes a RedirectionManager Object
    this.redirect = new RedirectionManager();
    this.output = null;
    this.fileContents = "";
  }
  
  @Override
	public boolean checkArgs(FileSystemI fs, String[] arguments, String fullInput) throws InvalidArgsProvidedException {
    //checks if user inputted a parameter or not
    if(arguments.length == 0){
      throw new InvalidArgsProvidedException("Error: Invalid Argument : No arguments should be given");
    }
    //checks if user inputted more than one parameter
    else if(arguments.length > 1){
      throw new InvalidArgsProvidedException("Error : Multiple Parameters have been provided : " + String.join(" ", arguments) + " Only one is required");
    }
    //checks if load was the first command inputted 
    else if(!checkCommandLog(fs)){
      throw new InvalidArgsProvidedException("Error: load was not the first command inputted");
    }
    return true;
	}
  
  /*
   * Things to work on:
   *    - JavaDoc
  */

  @Override
  public String run(FileSystemI filesys, String arguments[], String fullInput, boolean val) {
    //Seperates the parameters from everything else from the user input
    String[] args = redirect.setParams(fullInput);
    try {
      //checks for valid arguments
      if (checkArgs(filesys, args, fullInput)) {
        //formats the filepath
        filePath = formatArguments(args);
        //checks if the filename is valid
        validateFileName(filesys, fullInput);
        //FileReader object that holds the file
        fileReader = new FileReader(filePath);
        //BufferedReader object to read the file
        reader = new BufferedReader(fileReader);
        String line = reader.readLine();
        while (line != null) {
          //If we are in the nodes section
          if (line.equals("NODES"))
            //add the nodes
            uploadNodes(line, filesys);
          //if we are in the command log section
          else if (line.equals("COMMAND LOG"))
            //add the command log
            uploadCommandLog(line, filesys);
          line = reader.readLine();
        }
      }
    } 
    //Invalid argument error
    catch (InvalidArgsProvidedException e) {
      return e.getLocalizedMessage();
    } 
    //Invalid path/file not found error
    catch (FileNotFoundException e) {
      return "Error: Invalid Path : " + args[0];
    } 
    //Issue with fileReader and/or BufferedReader
    catch (IOException e) {
      return "Issues with Load";
    }
    return output;
  }
  
  public String getFileContents(FileSystemI filesys, String[] args, String fullInput, boolean val){
    try {
      //checks if user tries to use redirection with this command
      redirect.isRedirectionableCommand(fullInput);
    } 
    //Redirection not compatible with this command
    catch (InvalidRedirectionError e) {
      return e.getLocalizedMessage();
    }
    //Output holds the result of the execution
    output = run(filesys, args, fullInput, false);
    //If there was an error that occured
    if(output != null){
      if(output.startsWith("Error:") || output.startsWith("Error :")) return output;
    }
    //return the fileContents that were read
    return fileContents.trim();
  }

  private void validateFileName(FileSystemI filesys, String fullInput) throws InvalidArgsProvidedException{
    //checks for valid filename
    if(!checkFileName(filePath, filesys)) {
      throw new InvalidArgsProvidedException("Error: Invalid File : " + fullInput);
    }
    //checks if the file inputted is a .json file
    if(filePath.contains(".")){
      if(!filePath.substring(filePath.length()-5, filePath.length()).equals(".json")) {
        throw new InvalidArgsProvidedException("Error: Invalid File : " + fullInput);
      }
    }
    //doesn't have .json at the end
    else filePath += ".json";
  }

  private boolean checkCommandLog(FileSystemI filesys) {
    //checks if load was the first command inputted 
    if(filesys.getCommandLog().size() <= 1) return true;
    return false;
  }

  private boolean checkFileName(String filePath, FileSystemI filesys){
    String fileName = "";
    //if given absolute path then grab the filename
    if(filePath.contains("\\")) filePath.substring(filePath.lastIndexOf("\\"), filePath.length());
    else fileName = filePath;
    //check if filename is valid
    if(filesys.isValidName(fileName)) return true;
    return false;
  }
  
  private void uploadNodes(String line, FileSystemI filesys) {
    try {
      //Two readLines need to be used to read the useless \n used to divide sections
      line = reader.readLine();
      line = reader.readLine();
      //Loop runs until the end of section has been reached
      while(!line.equals("}")) {
        String[] nodeInformation = new String[4];
        //Grabs the data for the nodes
        for(int i = 0; i < nodeInformation.length; i++) {
          nodeInformation[i] = line;
          fileContents += nodeInformation[i] + "\n";
          line = reader.readLine();
        }
        //create a ndoe using the data read from file
        createNode(nodeInformation, filesys);
        line = reader.readLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void uploadCommandLog(String line, FileSystemI filesys) {
    try {
      //Two readLines need to be used to read the useless \n used to divide sections
      line = reader.readLine();
      line = reader.readLine().trim().replaceAll("\"", "");
      //Loop runs until the end of section has been reached
      while(!line.equals("}")) {
        //Adds the command log from the json file to the filesystem command log
        filesys.getCommandLog().add(line);
        fileContents += line + "\n";
        line = reader.readLine().trim().replaceAll("\"", "");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void createNode(String[] nodeInformation, FileSystemI filesys) {
    //Array to hold the parsed data of the node
    String[] parsedNodeInformation = new String[4];
    //for loop to loop through the data read from file
    for(int i = 0; i < nodeInformation.length; i++) {
      //parses the line read from file to extract the needed data
      String parseInfo = nodeInformation[i].split(":")[1].trim();
      parseInfo = parseInfo.substring(1, parseInfo.length()-1);
      parsedNodeInformation[i] = parseInfo.trim();
    }
    //If the data that we parsed does not represent the root node (cause root node is already in filesystem when created)
    if(!parsedNodeInformation[0].equals(filesys.getRoot().getName())) {
      //Create the node
      Node newNode = new Node.Builder(Boolean.valueOf(parsedNodeInformation[1]), parsedNodeInformation[0])
                             .setContent(parsedNodeInformation[3])
                             .build();
      //Replaces the "\n" to \n character as we replaced them before in save command
      newNode.setContent(newNode.getContent().replaceAll("\\\\n", "\n"));
      //if we are in the parent node then just add to the parent node arraylist
      if(parsedNodeInformation[2].equals(filesys.getCurrent().getName()))
        filesys.addToDirectory(newNode);
      else{
        //loop therough and add to the filesystem
        addNodeToFileSystem(newNode, parsedNodeInformation[2], filesys);
      }
    }
  }
  
  private void addNodeToFileSystem(Node newNode, String parentName, FileSystemI filesys) {
    filesys.assignCurrent(filesys.getRoot());
    //Traverse the filesystem using Depth First Search
    traverseFileSystem(filesys.getCurrent(), parentName, newNode,filesys);
  }
  
  private void traverseFileSystem(Node current, String desiredParentName, Node newNode, FileSystemI filesys) {
    //If we have reached the parent node then just add the newNode to the parent node arraylist
    if(current.getName().equals(desiredParentName)) {
      newNode.setParent(current);
      filesys.addToDirectory(newNode);
    }
    else {
      //loop through the arraylist of nodes
      for(int i = 0; i < current.getList().size(); i++) {
        filesys.assignCurrent(current.getList().get(i));
        //traverse teh filesystem again
        traverseFileSystem(filesys.getCurrent(), desiredParentName, newNode, filesys);
      }
    }
  }
  
  private String formatArguments(String[] args) {
    //replaces / with \\
    if(args[0].contains("/")) return args[0].replace("/", "\\\\");
    //replaces \ with \\
    if(args[0].contains("\\")) return args[0].replace("\\", "\\\\");
    return args[0];
  }
  
}