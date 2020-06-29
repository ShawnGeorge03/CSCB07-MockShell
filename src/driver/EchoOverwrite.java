package driver;

public class EchoOverwrite extends Echo{
  
  //private String argument = "";
  public void execute(String fileContents, String fileName) {
    
    if(!fileName.contains(".txt")) fileName += ".txt";
    
    String newFileName = fileName;
    if(findFileGivenRelative(newFileName) != null) {
      //System.out.println("File Found");
      Node file = findFileGivenRelative(newFileName);
      file.content = fileContents;
    }
    else {
      //System.out.println("File not found");
      Node newFile = new Node();
      newFile.isDir = false;
      newFile.content = fileContents;
      newFile.name = newFileName;
      newFile.parent = filesys.getCurrent();
      filesys.addToDirectory(newFile);
    }
  }
//  public void execute(String[] input_arguments) {
//    for(int i = 0; i < input_arguments.length; i++) {
//      argument += input_arguments[i];
//    }
//    
//    String file_name = argument.substring(argument.lastIndexOf('>')+1, argument.length()) + ".txt";
//    if(find_file(file_name) != null) {
//      //System.out.println("File Found");
//      Node file = find_file(file_name);
//      file.content = argument.substring(0, argument.indexOf(">")).replaceAll("\"", "");
//    }
//    else {
//      //System.out.println("File not found");
//      Node new_file = new Node();
//      new_file.isDir = false;
//      new_file.content = argument.substring(0, argument.indexOf(">")).replaceAll("\"", "");
//      new_file.name = file_name;
//      new_file.parent = filesys.getCurrent();
//      this.filesys.addToDirectory(new_file);
//    }
//  }
  
}
