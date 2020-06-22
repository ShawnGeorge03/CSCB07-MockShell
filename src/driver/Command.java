package driver;

public class Command {

  String command;
  String[] arguments;
  
  public Command(String command, String[] arguments) {
    this.command = command;
    this.arguments = arguments;
  }
  
  public void run(String command, String[] arguments) {
    //Add run code for each specific command
  }
  

}
