package driver;

import java.util.ArrayList;
import java.util.Arrays;

public class Command {

  String usercommand;
  ArrayList<String> arguments = new ArrayList<String>();
  String[] splitInput;
  String command;

  public Command(String parsedInput) {
    this.splitInput = parsedInput.split(" ");
    this.command = splitInput[0];
    String args[] = Arrays.copyOfRange(splitInput, 1, splitInput.length);
    run(command, args);
    
  }

  public void run(String command, String[] arguments) {
    switch(command) {
      case "cd":
        Cd cd_exe = new Cd(arguments);
        cd_exe.run();
        break;
      case "pwd":
        break;
      case "ls":
        break;
      case "mkdir":
        break;
      case "popd":
        break;
      case "pushd":
        break;
      case "cat":
        break;
      case "man":
        break;
      case "speak":
        break;
      
    }
    
  }

//  public static void main(String[] args) {
//    String input = "cd ..";
//    Command new_command = new Command(input);
//    new_command.usercommand = new_command.splitInput[0];
//    for (int i = 1; i < new_command.splitInput.length; i++) {
//      new_command.arguments.add(new_command.splitInput[i]);
//    }
//  }

}
