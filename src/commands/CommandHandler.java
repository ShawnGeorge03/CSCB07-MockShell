package commands;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Class CommandHandler is responsible for creating instances of the requested command and running
 * them
 */
public class CommandHandler {

  /**
   * Declare instance variable of String array to hold the command and its arguments
   */
  String[] splitInput;
  /**
   * Declare instance variable of String to hold the actual command
   */
  String command;
  /**
   * Declare instance variable of HashMap to hold all of the commands this program provides
   */
  HashMap<String, String> commandMap;

  boolean speakMode = false;

  private ErrorHandler errorManager;
  /**
   * Declare instance variable of String to hold the output that the command may give
   */
  String output;

  /**
   * Constructor for CommandHandler which initializes the instance variables and populates the
   * commandMap with commands
   */
  public CommandHandler() {
    errorManager = new ErrorHandler();
    commandMap = new HashMap<String, String>();
    intizializeCommandMap();
  }

  /**
   * Populates HashMap with commands and their respective methods
   */
  public void intizializeCommandMap() {
    commandMap.put("speak", "commands.TextSpeech");
    commandMap.put("mkdir", "commands.Mkdir");
    commandMap.put("cd", "commands.Cd");
    commandMap.put("ls", "commands.Ls");
    commandMap.put("pwd", "commands.Pwd");
    commandMap.put("pushd", "commands.Push");
    commandMap.put("popd", "commands.Pop");
    commandMap.put("history", "commands.History");
    commandMap.put("cat", "commands.Cat");
    commandMap.put("echo", "commands.Echo");
    commandMap.put("man", "commands.Man");
    commandMap.put("exit", "commands.Exit");

  }

  /**
   * Splits up command and arguments, then sends it to the run method
   * 
   * @param parsedInput the parsed input containing the command, and its arguments
   */
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
    run(command, args, parsedInput);
    if (command.equals("speak") && parsedInput.endsWith("QUIT"))
      speakMode = false;


  }

  /**
   * Calls the requested command's run method
   * 
   * @param command the name of the command
   * @param args the string array of arguments
   * @param fullInput the raw input that the user gave to JShell
   */
  public void run(String command, String[] args, String fullInput) {
    if (!commandMap.containsKey(command)) {
      output = errorManager.getError("Invalid Command", command);
    } else {
      try {
        String className = commandMap.get(command);

        try {

          CommandI commandObj = (CommandI) Class.forName(className)
              .getDeclaredConstructor().newInstance();

          output = commandObj.run(args, fullInput, speakMode);

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
    if (output != null)
      System.out.println(output);
  }
}
