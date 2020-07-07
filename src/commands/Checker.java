package commands;

public class Checker {
  
  private CommandHandler manager;
  
  public String userInput;
  
  public Checker() {
    this.userInput = "";
    this.manager = new CommandHandler();
  }
  
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
