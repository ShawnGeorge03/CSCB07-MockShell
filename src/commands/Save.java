package commands;

import java.io.FileWriter;
import java.io.IOException;
import data.*;

/*
 * Things to Work on:
 *  - if user does not include a .json at the end
 *      - if they input a .txt then give an error
 *      - if they don't give any file formet then add .json at the end
 *  - check code for any bugs/error checking 
 *  - JavaDocs
 *  - TestCases
*/
public class Save implements CommandI{

  private FileWriter writer;
  private String filePath;
  private ErrorHandler error;
  private String output;
  
  public Save(){
    this.error = new ErrorHandler();
    this.output = null;
  }
  
  /*
   * Things to work on:
   *    - Error Checking
   *        - check if a fileName is valid
   *    - JavaDoc
   *    - Test cases
  */
  
  @Override
  public String run(FileSystemI filesys, String[] args, String fullInput, boolean val) {
    if(args.length > 0) {
      filePath = formatArguments(args);
      if(!filePath.substring(filePath.length()-4, filePath.length()).equals(".json")) {
        System.out.println("The final file is not a .json file type");
        return null;
      }
      //System.out.println(filePath);
      try {
        writer = new FileWriter(filePath); 
        
        writer.write("NODES\n{\n");
        storeNodeInformation(writer, filesys);
        writer.write("}");
        
        writer.write("\n\nFILESYSTEM\n{\n");
        storeFileSystem(writer, filesys);
        writer.write("}");
             
        writer.write("\n\nCOMMAND LOG\n{\n");
        storeCommandHistoryToFile(writer, filesys);
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
      writer.write("\t\"isDir\" : \"" + current.getisDir() + "\"\n");
      if(current.getParent() != null)
        writer.write("\t\"parent\" : \"" + current.getParent().getName() + "\"\n");
      else
        writer.write("\t\"parent\" : \"null\"\n");
      writer.write("\t\"content\" : \"" + current.getContent() + "\"\n\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void storeCommandHistoryToFile(FileWriter writer, FileSystemI filesys) {
    for(int i = 0; i < filesys.getCommandLog().size(); i++) {
      try {
        writer.write("\t\"" + filesys.getCommandLog().get(i) + "\"\n");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
}
