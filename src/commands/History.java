package commands;

import java.util.Arrays;
import data.FileSystem;

/**
 * Class history keeps track of all inputs given to JShell
 */
public class History implements CommandI {
  
  /**
   * Declare instance variable of ErrorHandler to handle error messages
   */
  private ErrorHandler err;
  /**
   * Declare instance variable of String to contain the output that the command may return
   */
  String output;
  
  /**
   * Constructor for History that initializes instance variables
   */
  public History() {
    this.err = new ErrorHandler();
    this.output = "";
  }
  
  /**
   * Parses input and then calls print method to print history
   * 
   * @param args  the string array that contains given arguments
   * @param fullInput  the string that contains the raw input given to JShell
   * @return the error message if there is any or the actual history
   */
  @Override
  public String run(String[] args, String fullInput, boolean val) {   
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
        output = err.getError("Invalid Argument", args[0]);
      }
    }else if(args.length > 1) {
      String parameter = Arrays.toString(args);
      parameter = parameter.substring(1, parameter.length() - 1).replace(",", "").trim();
      output = err.getError("Mulptile parameters provided", parameter);
    }
    return output.trim();
  }
  
  public void addCommands(String Command) {
    FileSystem.getCommandLog().add(Command);
  }
  
  public int getCommandLogSize() {
    return FileSystem.getCommandLog().size();
  }
  
  public void printLastXCommands(int x) {
    for(int i = getCommandLogSize() - x; i < getCommandLogSize(); i++) {
      if(i < 0) continue;
      output += (i+1)+". " + FileSystem.getCommandLog().get(i) + "\n";
    }
  }
}
