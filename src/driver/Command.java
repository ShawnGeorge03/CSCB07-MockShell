package driver;

import java.util.Arrays;

public class Command {

  String[] splitInput;
  String command;
  boolean speakMode = false;
  
  private TextSpeech tts;
  private PushAndPop pushpop;
  private PushAndPop popStack;
  private Pwd pwd_exe;
  private History history;
  private Cat cat;
  private Echo echo;

  public Command() {
   this.tts = new TextSpeech();
   this.pushpop = new PushAndPop();
   this.popStack = new PushAndPop();
   this.pwd_exe = new Pwd();
   this.cat = new Cat();
   this.history = new History();
   this.echo = new Echo();
  }
  
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
        tts.speak(arguments);
        if(arguments.length == 0) speakMode = true;
        if(tts.containsQUIT(arguments)) speakMode = false;
        break;
      case "mkdir":
        Mkdir mkdir_exe = new Mkdir(arguments);
        mkdir_exe.MakeDirectory();
        break;
      case "cd":
        Cd cd_exe = new Cd(arguments);
        boolean dummy = cd_exe.run();
        break;
      case "ls":
        Ls ls_exe = new Ls(arguments);
        ls_exe.listDirectory();
        break;
      case "pwd":
        pwd_exe.printDirectory();
        break;
      case "pushd":
        pushpop.pushPath(arguments);
        break;
      case "popd":
    	popStack.pop();
    	break;
      case "history":
        if(arguments.length == 0) {
          history.printLastXCommands(history.getCommandLogSize());
        }else if(arguments.length == 1) {
          history.printLastXCommands(Integer.valueOf(arguments[0]));
        }else {
          System.out.println("Number of arguments required excceded");
        }
        break;
      case "cat":
        cat.readFile(arguments);
        break;
      case "echo":
        echo.compile_arguments(fullInput);
        break;
      case "man":
        break;
      case "exit":
        System.exit(0);
       default:
         System.out.println("Invalid Command"); 
    } 
  }
}
