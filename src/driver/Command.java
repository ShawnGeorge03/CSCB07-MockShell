package driver;

import java.util.ArrayList;

public class Command {

  String usercommand;
  ArrayList<String> arguments = new ArrayList<String>();
  String[] splitInput;
  
  public Command(String parsedInput) {
	  this.splitInput = parsedInput.split(" ");
  }
  
  public void run(String command, String[] arguments) {
    
  }
  
  public static void main(String[] args) {
	  Command new_command = new Command();
	  new_command.usercommand = splitInput[0];
	  for (int i = 1; i < new_command.splitInput.length; i++) {
		  new_command.arguments.add(new_command.splitInput[i]);
	  }
	  
  }
  

}
