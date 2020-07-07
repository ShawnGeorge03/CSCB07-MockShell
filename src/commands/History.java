package commands;

import java.util.Arrays;
import data.FileSystem;

public class History implements CommandI {
  
  private ErrorHandler err;
  
  public History() {
    this.err = new ErrorHandler();
  }
  
  @Override
  public void run(String[] args, String fullInput) {
    runHistory(args);
  }
  
  public void addCommands(String Command) {
    FileSystem.getCommandLog().add(Command);
  }
  
  public int getCommandLogSize() {
    return FileSystem.getCommandLog().size();
  }
  
  public void runHistory(String args[]) {
    
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
        err.getError("Invalid Argument", args[0]);
      }
    }else if(args.length > 1) {
      String parameter = Arrays.toString(args);
      parameter = parameter.substring(1, parameter.length() - 1).replace(",", "").trim();
      err.getError("Mulptile parameters provided", parameter);
    }
    
  }
  
  public void printLastXCommands(int x) {
    for(int i = getCommandLogSize() - x; i < getCommandLogSize(); i++) {
      if(i < 0) continue;
      System.out.println((i+1)+". " + FileSystem.getCommandLog().get(i));
    }
  }
}
