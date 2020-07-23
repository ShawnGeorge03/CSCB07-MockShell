package commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import data.FileSystem;
import data.Node;

public class Load implements CommandI{

  private FileReader fileReader;
  private BufferedReader reader;
  private String filePath;
  private String output;
  private ErrorHandler error;
  private FileSystem filesys;
  
  public Load(){
    this.filesys = FileSystem.getFileSys();
    this.error = new ErrorHandler();
    this.output = null;
  }
  
  /*
   * Things to work on:
   *    - Error Checking
   *    - JavaDoc
   *    - Test cases
  */

  @Override
  public String run(String[] args, String fullInput, boolean val) {
    System.out.println(args[0]);
    if(args[0].length() > 0 && checkCommandLog()) {
      filePath = formatArguments(args);
      try {
        fileReader = new FileReader(filePath);
        reader = new BufferedReader(fileReader);
        String line = reader.readLine();

        while(line != null) {
          if(line.equals("NODES")) uploadNodes(line);
          else if(line.equals("COMMAND LOG")) {
            uploadCommandLog(line);
          }
          line = reader.readLine();
        }

      } catch (FileNotFoundException e) { //from trying to find file path 
        e.printStackTrace();
      } catch (IOException e) { // from reading a line
        e.printStackTrace();
      }
    }
    else{
      if(checkCommandLog()) output = error.getError("No parameters provided", fullInput);
      else System.out.println("Error (load was not the first command inputted)");
    }
    
    return output;
  }
  
  private boolean checkCommandLog() {
    if(this.filesys.getCommandLog().size() == 1) return true;
    return false;
  }
  
  private void uploadNodes(String line) {
    try {
      line = reader.readLine();
      line = reader.readLine();
      while(!line.equals("}")) {
        String[] nodeInformation = new String[4];
        for(int i = 0; i < nodeInformation.length; i++) {
          nodeInformation[i] = line;
          line = reader.readLine();
        }
        createNode(nodeInformation);
        line = reader.readLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void uploadCommandLog(String line) {
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
  
  private void createNode(String[] nodeInformation) {
    String[] parsedNodeInformation = new String[4];
    for(int i = 0; i < nodeInformation.length; i++) {
      String parseInfo = nodeInformation[i].replaceAll("\"", "").trim();
      parsedNodeInformation[i] = parseInfo.split(":")[1].trim();
    }
    if(!parsedNodeInformation[0].equals(filesys.getRoot().getName())) {
      Node newNode = new Node();
      newNode.setName(parsedNodeInformation[0]);
      newNode.setDir(Boolean.valueOf(parsedNodeInformation[1]));
      newNode.setContent(parsedNodeInformation[3]);
      if(parsedNodeInformation[2].equals(this.filesys.getCurrent().getName()))
        this.filesys.addToDirectory(newNode);
      else{
        addNodeToFileSystem(newNode, parsedNodeInformation[2]);
      }
    }
  }
  
  private void addNodeToFileSystem(Node newNode, String parentName) {
    this.filesys.assignCurrent(this.filesys.getRoot());
    traverseFileSystem(this.filesys.getCurrent(), parentName, newNode);
  }
  
  private void traverseFileSystem(Node current, String desiredParentName, Node newNode) {
    if(current.getName().equals(desiredParentName)) {
      newNode.setParent(current);
      this.filesys.addToDirectory(newNode);
    }
    else {
      for(int i = 0; i < current.getList().size(); i++) {
        this.filesys.assignCurrent(current.getList().get(i));
        traverseFileSystem(this.filesys.getCurrent(), desiredParentName, newNode);
      }
    }
  }
  
  private String formatArguments(String[] args) {
    if(args[0].contains("/")) return args[0].replace("/", "\\\\");
    if(args[0].contains("\\")) return args[0].replace("\\", "\\\\");
    return args[0];
  }
  
}
