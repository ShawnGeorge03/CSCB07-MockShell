package driver;

public class Checker {
  
  private Command commandHandler;
  
  public String userInput;
  
  public Checker() {
    this.userInput = "";
    this.commandHandler = new Command();
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

    commandHandler.setCommand(parsedInput);
  }
  
}
