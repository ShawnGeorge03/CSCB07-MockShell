package commands;

import java.io.FileWriter;
import java.io.IOException;
import data.*;

/*
 * Things to Work on:
 *  - if user does not put in an absolute path
 *  - if user does not include a .json at the end
 *      - if they input a .txt then give an error
 *      - if they don't give any file formet then add .json at the end
 *  - check code for any bugs/error checking 
*/
public class Save implements CommandI{

  private FileWriter writer;
  private String filePath;
  private FileSystem filesys;
  private ErrorHandler error;
  private String output = null;
  
  public Save(){
    this.filesys = FileSystem.getFileSys();
    this.error = new ErrorHandler();
  }
  
  @Override
  public String run(String[] args, String fullInput, boolean val) {
    if(args.length > 0) {
      filePath = formatArguments(args);
      System.out.println(filePath);
      try {
        writer = new FileWriter(filePath); 
        writer.write("FILESYSTEM\n{\n");
        storeFileSystem(writer);
        writer.write("}");
        //writer.write("If this doesn't work then I'll be sad");
        
        writer.write("\n\nNODES\n{\n");
        storeNodeInformation(writer);
        writer.write("}");
     
        writer.write("\n\nCOMMAND LOG\n{\n");
        storeCommandHistoryToFile(writer);
        writer.write("}");
        
        writer.close();
      } catch (Exception e) {
          e.printStackTrace();
      }
    }
    else output = error.getError("No parameters provided", fullInput);
    return output;
  }
  
  private String formatArguments(String[] args) {
    if(args[0].contains("/")) return args[0].replace("/", "\\\\");
    if(args[0].contains("\\")) return args[0].replace("\\", "\\\\");
    return args[0];
  }
  
  private void storeFileSystem(FileWriter writer) {
    Node root = filesys.getRoot();
    traverseFileSystem(root, writer, 1, 1);
  }
  
  private void storeNodeInformation(FileWriter writer){
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
      String output = "";
      for(int i = 0; i < depth; i++) {
        output += "\t";
      }
      writer.write(output + "\"" + current.getName() + "\"\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void addNodeInformationToFile(Node current, FileWriter writer, int depth) {
    try {
      writer.write("\t\"name\" : \"" + current.getName() + "\"\n");
      writer.write("\t\"isDir\" : \"" + current.isDir() + "\"\n");
      if(current.getParent() != null)
        writer.write("\t\"parent\" : \"" + current.getParent().getName() + "\"\n");
      else
        writer.write("\t\"parent\" : \"null\"\n");
      writer.write("\t\"content\" : \"" + current.getContent() + "\"\n\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void storeCommandHistoryToFile(FileWriter writer) {
    for(int i = 0; i < filesys.getCommandLog().size(); i++) {
      try {
        writer.write("\t\"" + filesys.getCommandLog().get(i) + "\"\n");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
}
