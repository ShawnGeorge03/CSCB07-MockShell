package commands;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import data.FileSystem;

public class CommandHandler {

  private FileSystem fs;
  private String[] splitInput;

  private String command;

  private HashMap<String, String> commandMap;

  private boolean speakMode = false;
  private boolean appendMode = false;
  private boolean overwriteMode = false;

  private ArrayList<String> redirectClasses;

  private ErrorHandler errorManager;

  String output;
  private String file;


  public CommandHandler() {
    fs = FileSystem.getFileSys();
    errorManager = new ErrorHandler();
    commandMap = new HashMap<String, String>();
    redirectClasses = new ArrayList<String>();
    intizializeCommandMap();
    redirectClasses.addAll(Arrays.asList("cat", "echo", "find", "history", "ls", "man", "pwd", "tree"));
  }

  public void setCommand(String parsedInput) {
    splitInput = parsedInput.split(" ");
    command = splitInput[0];
    String args[] = Arrays.copyOfRange(splitInput, 1, splitInput.length);

    if (speakMode) {
      this.command = "speak";
      args = splitInput;
    }

    if (command.equals("speak") && args.length == 0)
      speakMode = true;

    if(redirectClasses.contains(command)) args = setOutputPath(command, parsedInput);

    if(args == null){
      outputResult(output);
      return;
    } 
    
    System.out.println("Overwrite : " + overwriteMode + "| Append : " + appendMode + "| File : " + file);
    overwriteMode = false;
    appendMode = false;
    System.out.println(Arrays.asList(args));
    
    run(command, args, parsedInput);

    if (command.equals("speak") && parsedInput.endsWith("QUIT"))
      speakMode = false;
  }

  private String[] setOutputPath(String command, String input) {
    String[] args = input.split(" ");

    if (Arrays.asList(args).contains(">")) {
      overwriteMode = true;
      file = args[args.length - 1];
      args = Arrays.copyOfRange(args, 0, Arrays.asList(args).indexOf(">"));
    } else if (Arrays.asList(args).contains(">>")) {
      appendMode = true;
      file = args[args.length - 1];
      args = Arrays.copyOfRange(args, 0, Arrays.asList(args).indexOf(">"));
    } else {
      if(input.contains(">") || input.contains(">>")){
        output = errorManager.getError("Invalid Argument", "Make sure each argument has space(s) between them");
      }else{
        args = Arrays.copyOfRange(input.split(" "), 1, input.split(" ").length);
      }
    }

    return args;
  }

  public void run(String command, String[] args, String fullInput) {

    if (!commandMap.containsKey(command)) {
      output = errorManager.getError("Invalid Command", command + " is not supported");
    } else {
      try {

        String className = commandMap.get(command);

        try {

          CommandI commandObj =
              (CommandI) Class.forName(className).getDeclaredConstructor().newInstance();

          output = commandObj.run(fs, args, fullInput, speakMode);

        } catch (InstantiationException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (IllegalArgumentException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        } catch (NoSuchMethodException e) {
          e.printStackTrace();
        } catch (SecurityException e) {
          e.printStackTrace();
        }
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
    outputResult(output);
  }

  private void outputResult(String result){

    if (output != null){
      if(output.startsWith("Error") || !appendMode || !overwriteMode){
        System.out.println(output);
      }else if(overwriteMode){
        fs.fileOverwrite(output, file);
      }else if(appendMode){
        fs.fileAppend(output, file);
      }
    }
  }

  private void intizializeCommandMap() {
    // Adds a key named speak and adds its path
    commandMap.put("speak", "commands.Speak");
    // Adds a key named mkdir and adds its path
    commandMap.put("mkdir", "commands.Mkdir");
    // Adds a key named cd and adds its path
    commandMap.put("cd", "commands.Cd");
    // Adds a key named ls and adds its path
    commandMap.put("ls", "commands.Ls");
    // Adds a key named pwd and adds its path
    commandMap.put("pwd", "commands.Pwd");
    // Adds a key named pushd and adds its path
    commandMap.put("pushd", "commands.Push");
    // Adds a key named popd and adds its path
    commandMap.put("popd", "commands.Pop");
    // Adds a key named history and adds its path
    commandMap.put("history", "commands.History");
    // Adds a key named cat and adds its path
    commandMap.put("cat", "commands.Cat");
    // Adds a key named echo and adds its path
    commandMap.put("echo", "commands.Echo");
    // Adds a key named man and adds its path
    commandMap.put("man", "commands.Man");
    // Adds a key named exit and adds its path
    commandMap.put("exit", "commands.Exit");
    // Adds a key named rm and adds its path
    commandMap.put("rm", "commands.Rm");
    // Adds a key named mv and adds its path
    commandMap.put("mv", "commands.Mv");
    // Adds a key named curl and adds its path
    commandMap.put("curl", "commands.Curl");
    // Adds a key named curl and adds its path
    commandMap.put("cp", "commands.Cp");
    // Adds a key named find and adds its path
    commandMap.put("find", "commands.Find");
    // Adds a key named save and adds its path
    commandMap.put("save", "commands.Save");
    // Adds a key named load and adds its path
    commandMap.put("load", "commands.Load");
  }
}
