package commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import data.FileSystemI;
import data.Node;
import errors.InvalidArgsProvidedException;

public class Load implements CommandI{

  private FileReader fileReader;
  private BufferedReader reader;
  private String filePath;
  private String output;
  private ErrorHandler error;
  
  public Load(){
    this.error = new ErrorHandler();
    this.output = null;
  }
  
  @Override
	public boolean checkArgs(FileSystemI fs, String[] arguments, String fullInput) throws InvalidArgsProvidedException {
		return false;
	}
  
  /*
   * Things to work on:
   *    - JavaDoc
   *    - Test cases
  */

  @Override
  public String run(FileSystemI filesys, String[] args, String fullInput, boolean val) {
    if(args[0].length() > 0 && checkCommandLog(filesys)) {
      filePath = formatArguments(args);
      try {
        validateFileName(filesys, fullInput);
        fileReader = new FileReader(filePath);
        reader = new BufferedReader(fileReader);
        String line = reader.readLine();
        while(line != null) {
          if(line.equals("NODES")) uploadNodes(line, filesys);
          else if(line.equals("COMMAND LOG")) {
            uploadCommandLog(line, filesys);
          }
          line = reader.readLine();
        }
      } catch (FileNotFoundException e) { 
        return "Error: Invalid Path : " + args[0];
      } catch (IOException e) { 
        return "Issues with Load";
      } catch( InvalidArgsProvidedException e){
        e.getLocalizedMessage();
      }
    }
    else{
      if(!checkCommandLog(filesys)) output = "Error: load was not the first command inputted";
      output = error.getError("No parameters provided", fullInput);
    }
    return output;
  }
  
  private void validateFileName(FileSystemI filesys, String fullInput) throws InvalidArgsProvidedException{
    if(!checkFileName(filePath, filesys)) {
      throw new InvalidArgsProvidedException("Error: Invalid File : " + fullInput);
    }
    if(filePath.contains(".")){
      if(!filePath.substring(filePath.length()-5, filePath.length()).equals(".json")) {
        throw new InvalidArgsProvidedException("Error: Invalid File : " + fullInput);
      }
    }
  }

  private boolean checkCommandLog(FileSystemI filesys) {
    if(filesys.getCommandLog().size() == 1) return true;
    return false;
  }

  private boolean checkFileName(String filePath, FileSystemI filesys){
    String fileName = "";
    if(filePath.contains("\\")) filePath.substring(filePath.lastIndexOf("\\"), filePath.length());
    else fileName = filePath;
    if(filesys.isValidName(fileName)) return true;
    return false;
  }
  
  private void uploadNodes(String line, FileSystemI filesys) {
    try {
      line = reader.readLine();
      line = reader.readLine();
      while(!line.equals("}")) {
        String[] nodeInformation = new String[4];
        for(int i = 0; i < nodeInformation.length; i++) {
          nodeInformation[i] = line;
          line = reader.readLine();
        }
        createNode(nodeInformation, filesys);
        line = reader.readLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void uploadCommandLog(String line, FileSystemI filesys) {
    try {
      line = reader.readLine();
      line = reader.readLine().trim().replaceAll("\"", "");
      while(!line.equals("}")) {
        filesys.getCommandLog().add(line);
        line = reader.readLine().trim().replaceAll("\"", "");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void createNode(String[] nodeInformation, FileSystemI filesys) {
    String[] parsedNodeInformation = new String[4];
    for(int i = 0; i < nodeInformation.length; i++) {
      String parseInfo = nodeInformation[i].replaceAll("\"", "").trim();
      parsedNodeInformation[i] = parseInfo.split(":")[1].trim();
    }
    if(!parsedNodeInformation[0].equals(filesys.getRoot().getName())) {
      Node newNode = new Node.Builder(Boolean.valueOf(parsedNodeInformation[1]), parsedNodeInformation[0])
                             .setContent(parsedNodeInformation[3])
                             .build();
      if(parsedNodeInformation[2].equals(filesys.getCurrent().getName()))
        filesys.addToDirectory(newNode);
      else{
        addNodeToFileSystem(newNode, parsedNodeInformation[2], filesys);
      }
    }
  }
  
  private void addNodeToFileSystem(Node newNode, String parentName, FileSystemI filesys) {
    filesys.assignCurrent(filesys.getRoot());
    traverseFileSystem(filesys.getCurrent(), parentName, newNode,filesys);
  }
  
  private void traverseFileSystem(Node current, String desiredParentName, Node newNode, FileSystemI filesys) {
    if(current.getName().equals(desiredParentName)) {
      newNode.setParent(current);
      filesys.addToDirectory(newNode);
    }
    else {
      for(int i = 0; i < current.getList().size(); i++) {
        filesys.assignCurrent(current.getList().get(i));
        traverseFileSystem(filesys.getCurrent(), desiredParentName, newNode, filesys);
      }
    }
  }
  
  private String formatArguments(String[] args) {
    if(args[0].contains("/")) return args[0].replace("/", "\\\\");
    if(args[0].contains("\\")) return args[0].replace("\\", "\\\\");
    return args[0];
  }
  
}
