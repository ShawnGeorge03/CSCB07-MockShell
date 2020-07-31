package commands;

import java.io.FileWriter;
import java.io.IOException;
import data.*;
import errors.FileException;
import errors.InvalidArgsProvidedException;
import errors.InvalidRedirectionError;

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
		if(arguments.length == 0){
      throw new InvalidArgsProvidedException("Error : No parameters provided");
    }else if(arguments.length > 1){
      throw new InvalidArgsProvidedException("Error : Multiple Parameters have been provided : " + String.join(" ", arguments) + " Only one is required");
    }
    return true;
	}

  /*
   * Things to work on: - JavaDoc - Test cases
   */

  @Override
  public String run(FileSystemI filesys, String[] args, String fullInput, boolean val) {
    try {
      redirect.isRedirectionableCommand(filesys, fullInput);
      if(checkArgs(filesys, args, fullInput)){
        filePath = formatArguments(args);
        try {
          validateFileName(filesys, fullInput);
          writer = new FileWriter(filePath);
          writer.write("NODES\n{\n");
          storeNodeInformation(writer, filesys);
          writer.write("}");
          fileContent += "\n";
          writer.write("\n\nFILESYSTEM\n{\n");
          storeFileSystem(writer, filesys);
          writer.write("}");
          fileContent += "\n";
          writer.write("\n\nCOMMAND LOG\n{\n");
          storeCommandHistoryToFile(writer, filesys);
          writer.write("}");
  
          writer.close();
        } catch(InvalidArgsProvidedException e1){
          return e1.getLocalizedMessage();
         // could not find file
        }catch (IOException e1) {
          output = "Error: Invalid Path : " + args[0];
        }
      }
    } catch (InvalidArgsProvidedException e) {
      return e.getLocalizedMessage();
    }
    return output;
  }

  public String getFileContent(FileSystemI filesys, String[] args, String fullInput, boolean val) {
    try {
      redirect.isRedirectionableCommand(filesys, fullInput);
    } catch (InvalidRedirectionError e) {
      return e.getLocalizedMessage();
    }
    output = run(filesys, args, fullInput, false);
    if(output != null){
      if(output.startsWith("Error:") || output.startsWith("Error :")) return output;
    }
    return fileContent.trim();
  }
  /*
  private String runSave(FileSystemI filesys, String[] args, String fullInput){
    if(args.length > 0) {
      filePath = formatArguments(args);
      validateFileName(filesys, fullInput);
      if(output != null) return output;
      try {
        writer = new FileWriter(filePath); 
        
        writer.write("NODES\n{\n");
        storeNodeInformation(writer, filesys);
        writer.write("}");
        fileContent += "\n";
        writer.write("\n\nFILESYSTEM\n{\n");
        storeFileSystem(writer, filesys);
        writer.write("}");
        fileContent += "\n";
        writer.write("\n\nCOMMAND LOG\n{\n");
        storeCommandHistoryToFile(writer, filesys);
        writer.write("}");
        
        writer.close();
      } catch(IOException e) { //could not find file
        output = "Error: Invalid Path : " + args[0];
      }
    }
    else output = error.getError("No parameters provided", fullInput);
    return output;
  }*/

  private void validateFileName(FileSystemI filesys, String fullInput) throws FileException{
    if(!checkFileName(filePath, filesys)) {
      throw new FileException("Error: Invalid File : " + fullInput);
    }
    if(filePath.contains(".")){
      if(!filePath.substring(filePath.length()-5, filePath.length()).equals(".json")) {
        throw new FileException("Error: Invalid File : " + fullInput);
      }
    }
    else filePath += ".json";
  }

  private boolean checkFileName(String filePath, FileSystemI filesys){
    String fileName = "";
    if(filePath.contains("\\")) filePath.substring(filePath.lastIndexOf("\\"), filePath.length());
    else fileName = filePath;
    if(filesys.isValidName(fileName)) return true;
    return false;
  }

  private String formatArguments(String[] args) {
    if(args[0].contains("/")) return args[0].replace("/", "\\\\");
    if(args[0].contains("\\")) return args[0].replace("\\", "\\\\");
    return args[0];
  }
  
  private void storeFileSystem(FileWriter writer, FileSystemI filesys) {
    Node root = filesys.getRoot();
    traverseFileSystem(root, writer, 1, 1);
  }
  
  private void storeNodeInformation(FileWriter writer, FileSystemI filesys){
    Node root = filesys.getRoot();
    traverseFileSystem(root, writer, 1, 2);
  }
  
  private void traverseFileSystem(Node root, FileWriter writer, int depth, int mode){
    if(mode == 1) addNodeNameToFile(root, writer, depth);
    if(mode == 2) addNodeInformationToFile(root, writer, depth);
    for(int i = 0; i < root.getList().size(); i++) {
      depth += 1;
      traverseFileSystem(root.getList().get(i), writer, depth, mode);
      depth -= 1;
    }
  }
  
  private void addNodeNameToFile(Node current, FileWriter writer, int depth) {
    try {
      String result = "";
      for(int i = 0; i < depth; i++) {
        result += "\t";
      }
      fileContent += result + "\"" + current.getName() + "\"\n";
      writer.write(result + "\"" + current.getName() + "\"\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void addNodeInformationToFile(Node current, FileWriter writer, int depth) {
    try {
      fileContent += "\t\"name\" : \"" + current.getName() + "\"\n";
      fileContent += "\t\"isDir\" : \"" + current.getisDir() + "\"\n";
      writer.write("\t\"name\" : \"" + current.getName() + "\"\n");
      writer.write("\t\"isDir\" : \"" + current.getisDir() + "\"\n");
      if(current.getParent() != null){
        fileContent += "\t\"parent\" : \"" + current.getParent().getName() + "\"\n";
        writer.write("\t\"parent\" : \"" + current.getParent().getName() + "\"\n");
      }
      else{
        fileContent += "\t\"parent\" : \"null\"\n";
        writer.write("\t\"parent\" : \"null\"\n");
      }
      writer.write("\t\"content\" : \"" + current.getContent() + "\"\n\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void storeCommandHistoryToFile(FileWriter writer, FileSystemI filesys) {
    for(int i = 0; i < filesys.getCommandLog().size(); i++) {
      try {
        fileContent += "\t\"" + filesys.getCommandLog().get(i) + "\"\n";
        writer.write("\t\"" + filesys.getCommandLog().get(i) + "\"\n");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
}
