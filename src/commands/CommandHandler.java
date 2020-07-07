package commands;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;

public class CommandHandler {

  String[] splitInput;
  String command;
  HashMap<String, String> commandMap;

  boolean speakMode = false;

  private ErrorHandler err;

  String output;

  public CommandHandler() {
    err = new ErrorHandler();
    commandMap = new HashMap<String, String>();
    intizializeCommandMap();
  }

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
    run(command, args, parsedInput);
    if (command.equals("speak") && parsedInput.endsWith("QUIT"))
      speakMode = false;
  }

  public void run(String command, String[] args, String fullInput) {
    if (!commandMap.containsKey(command)) {
      output = err.getError("Invalid Command", command);
    } else {
      try {
        String className = commandMap.get(command);

        try {

          CommandI commandObj =
              (CommandI) Class.forName(className).getDeclaredConstructor().newInstance();

          output = commandObj.run(args, fullInput);

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
    if(output != null) System.out.println(output);
  }
}
