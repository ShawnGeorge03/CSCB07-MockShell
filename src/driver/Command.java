package driver;

import java.util.ArrayList;

public class Command {

  String command;
  ArrayList<String> arguments = new ArrayList<String>();
  
  public Command(String command, ArrayList<String> arguments) {
    this.command = command;
    this.arguments = arguments;
  }
  
  public void run(String command, String[] arguments) {
    //Add run code for each specific command
  }
  

}
