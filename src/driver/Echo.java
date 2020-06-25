package driver;

import java.util.Arrays;

public class Echo {
  
  FileSystem filesys = null;
  
  public Echo () {
    this.filesys = FileSystem.getFileSys();
  }
  
  public void eraseFile(String filePath) {
    
    
  }
  
  public void appendToFile(String filePath) {
    
  }
  
  public void echo(String input) {
   String[] partsOfInput = input.split("\"");
   String mode = null;
   String[] filePath = null;
   
   if(partsOfInput.length == 2) {
     System.out.println(partsOfInput[1]);
     return;
   }else {
      mode = partsOfInput[2].split("\\s+")[1];
     filePath = Arrays.copyOfRange(partsOfInput[2].split("\\s+"), 2, 
         partsOfInput[2].split("\\s+").length);
   }
   
   if(filePath.length == 0) {
     System.out.println("Not Files provided");
     return;
   }
   for(int i = 0; i < filePath.length; i++) {
     if(mode.contentEquals(">")) { 
       eraseFile(filePath[i]);
       appendToFile(filePath[i]);
     }else if(mode.contentEquals(">>")) {
       appendToFile(filePath[i]);
     }
   }
  }
  
}

