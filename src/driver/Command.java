package driver;

import java.util.ArrayList;
import java.util.Arrays;

public class Command {

  String usercommand;
  ArrayList<String> arguments = new ArrayList<String>();
  String[] splitInput;
  String command;
  boolean speakMode = false;
  
  public void setCommand(String parsedInput) {
    splitInput = parsedInput.split(" ");
    command = splitInput[0];
    String args[] = Arrays.copyOfRange(splitInput, 1, splitInput.length);
    if(speakMode) {
      command = "speak";
      args = splitInput;
    }

    run(command, args, parsedInput);
  }

  public void run(String command, String[] arguments, String fullInput) {  

    switch(command) {        
      case "speak":
        TextSpeech tts = new TextSpeech();
        if(tts.containsQUIT(arguments)) {
          speakMode = false;
        }else {
          speakMode = true;
        }
        tts.speak(arguments);
        break;
      case "mkdir":
        Mkdir mkdir_exe = new Mkdir(arguments);
        mkdir_exe.MakeDirectory();
      case "cd":
        Cd cd_exe = new Cd(arguments);
        cd_exe.run();
        break;
      case "ls":
        Ls ls_exe = new Ls(arguments);
        ls_exe.listDirectory();
      case "pwd":
        Pwd pwd_exe = new Pwd();
        pwd_exe.printDirectory();
      case "pushd":
        break;
      case "popd":
        break;
      case "history":
        History history = new History();
        if(arguments.length == 0) {
          history.printLastXCommands(history.getCommandLogSize());
        }else if(arguments.length == 1) {
          history.printLastXCommands(Integer.valueOf(arguments[0]));
        }else {
          System.out.println("Number of arguments required excceded");
        }
        break;
      case "cat":
        Cat cat = new Cat();
        cat.readFile(arguments);
        break;
      case "echo":
        Echo echo = new Echo();
        echo.compile_arguments(fullInput);
        break;
      case "man":
        break;
       default:
        
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
