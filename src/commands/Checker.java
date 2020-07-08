package commands;

/**
 * Class Checker is responsible for checking if the user gave valid input and splits up the command
 * from the arguments
 */
public class Checker {
  
  /**
   * Declare instance of CommandHandler so this can send command and arguments to it
   */
  private CommandHandler manager;
  
  /**
   * Declare instance of String to contain the user's input
   */
  public String userInput;
  
  /**
   * Constructor for Checker which initializes the instance variables
   */
  public Checker() {
    this.userInput = "";
    this.manager = new CommandHandler();
  }
  
  /**
   * Parses the raw input that the user gives into JShell, which then sends it to CommandHandler if
   * valid
   * 
   * @param input  the raw input provided by the user in JShell
   */
  public void parseInput(String input) {
    String parsedInput = "";
    String commandName = "";
    String args = "";
    if(input.contains("\"")) {
      commandName = input.substring(0, input.indexOf("\"")).trim().replaceAll("\\s+", " ");
      args = input.substring(input.indexOf("\""), input.length());
      parsedInput = commandName + " " + args;
    }
    else if(input.contains("'")) {
      commandName = input.substring(0, input.indexOf("'")).trim().replaceAll("\\s+", " ");
      args = input.substring(input.indexOf("'"), input.length());
      parsedInput = commandName + " " + args;
    }
    else parsedInput = input.trim().replaceAll("\\s+", " ");

    manager.setCommand(parsedInput);
  }
  
}
