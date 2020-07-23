// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: patelt26
// UT Student #: 1005904103
// Author: Shawn Santhoshgeorge
//
// Student2:
// UTORID user_name: shaiskan
// UT Student #: 1006243940
// Author: Keshavaa Shaiskandan
//
// Student3:
// UTORID user_name: patelt26
// UT Student #: 1005904103
// Author: Tirth Patel
//
// Student4:
// UTORID user_name: pate1101
// UT Student #: 1006315765
// Author: Abhay Patel
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package commands;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Class CommandHandler is responsible for creating instances of the requested command and running
 * them
 * 
 * Note : Part of this code was taken from Lab6 src.lab6_project.executionOfProgram.java file
 */
public class CommandHandler {

  /**
   * Declare instance variable of String array to hold the command and its arguments
   */
  private String[] splitInput;
  /**
   * Declare instance variable of String to hold the actual command
   */
  private String command;
  /**
   * Declare instance variable of HashMap to hold all of the commands this program provides
   */
  private HashMap<String, String> commandMap;

  private boolean speakMode = false;

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
    // Initializes a ErrorHandler Object
    errorManager = new ErrorHandler();
    // Creates a HashMap Object called commandMap
    commandMap = new HashMap<String, String>();
    // Initializes the HashMap with the keys and values
    intizializeCommandMap();

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

  /**
   * Splits up command and arguments, then sends it to the run method
   * 
   * @param parsedInput  the parsed input containing the command, and its arguments
   */
  public void setCommand(String parsedInput) {
    // Initializes an array containing the words of the parsedInput
    splitInput = parsedInput.split(" ");
    // Retrieving the command portion of the the user input from array
    command = splitInput[0];
    // Retrieving the arguments portion of the the user input from array
    String args[] = Arrays.copyOfRange(splitInput, 1, splitInput.length);

    // If we are in speak mode
    if (speakMode) {
      // Sets the command to the String speak
      this.command = "speak";
      // Sets the arguments to the splitInput
      args = splitInput;
    }

    // If the command was speak and there was no user input
    if (command.equals("speak") && args.length == 0)
      // The console enters into speak mode
      speakMode = true;

    // Calls the function to run the command
    run(command, args, parsedInput);

    // If the command was speak and the user input ends with the special keyword QUIT
    if (command.equals("speak") && parsedInput.endsWith("QUIT"))
      // The console exits into speak mode
      speakMode = false;
  }

  /**
   * Calls the requested command's run method
   * 
   * @param command  the name of the command
   * @param args  the string array of arguments
   * @param fullInput  the raw input that the user gave to JShell
   */
  public void run(String command, String[] args, String fullInput) {

    // Check if the command is supported
    if (!commandMap.containsKey(command)) {
      // Sets the error as Invalid Command
      output = errorManager.getError("Invalid Command", command + " is not supported");
    } else {
      try {

        // Gets the class path of the command that needs to ne run
        String className = commandMap.get(command);

        try {

          // Created an instance of the Class and initialized it
          CommandI commandObj =
              (CommandI) Class.forName(className).getDeclaredConstructor().newInstance();

          // Calls the run command in that respective class and collects the output
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
