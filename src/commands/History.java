package commands;

import data.FileSystem;

public class History {
  
  public void addCommands(String Command) {
    FileSystem.getCommandLog().add(Command);
  }
  
  public int getCommandLogSize() {
    return FileSystem.getCommandLog().size();
  }
  
  public void run(String args[]) {
    
    if(args.length == 0) {
      printLastXCommands(getCommandLogSize());
    }else if(args.length == 1) {
      int number = 0;
      boolean numeric = true;
      
      try {
        number = Integer.valueOf(args[0]);
      } catch (NumberFormatException e) {
        numeric = false;
      }
      
      if(numeric && number >= 0 && number % 1 == 0) {
        printLastXCommands(number); 
      } else {
        System.out.println("Invalid Argument(s)");
      }
    }else if(args.length > 1) {
      System.out.println("Invalid Argument(s)");
    }
    
  }
  
  public void printLastXCommands(int x) {
    for(int i = FileSystem.getCommandLog().size() - x; i < FileSystem.getCommandLog().size(); i++) {
      if(i < 0) continue;
      System.out.println((i+1)+". " + FileSystem.getCommandLog().get(i));
    }
  }
  
  

}
