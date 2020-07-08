package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.*;
import data.FileSystem;

/**
 * Class Cd is responsible for changing directories within the FileSystem
 */
public class Cd extends DirectoryManager implements CommandI {

  ArrayList<String> cdArguments;
  boolean successfulPath = false;
  String errorOutput;
  /**
   * Declare instance of FileSystem so we can access the filesystem
   */
  FileSystem filesys;
  /**
   * Declare instance of ErrorHandler to handle error messages
   */
  ErrorHandler error;

  /**
   * Constructor for class Cd which initalizes instance variables
   */
  public Cd() {
    filesys = FileSystem.getFileSys();
    error = new ErrorHandler();

  }

  /**
   * Starting run method which checks if arguments were given, then passes it to another run method
   * which processes the command
   * 
   * @param args the string array of arguments
   * @param fullInput the full line of input that the user gives into JShell
   * @return any error messages if there are any
   */
  public String run(String[] args, String fullInput, boolean val) {
    if (args.length == 0) {
      errorOutput = error.getError("No parameters provided", "");
      return errorOutput;
    } else if (args.length > 1) {
      String errorOutput = error.getError("Mulptile parameters provided",
          fullInput.substring(fullInput.indexOf("cd") + 2).trim());
      return errorOutput;
    }

    if (!run(args)) {
      String errorOutput = error.getError("Invalid Directory",
          fullInput.substring(fullInput.indexOf("cd") + 2).trim());
      return errorOutput;
    }
    return null;
  }

  /**
   * Checks if the argument follows the pattern of ../ in which each pair of periods dictates how many
   * directories to go up
   * 
   * @return true if argument matches pattern, false otherwise
   */
  public boolean isBackwards() {
    String cur = this.cdArguments.get(0);
    if (Pattern.matches("(../)+", cur) || (Pattern.matches("(../..)+", cur))) {
      return true;
    }
    return false;
  }

  /**
   * Main run method that executes the performance of changing directories based on what argument is
   * given. If argument is ".", nothing happens If argument is "..", go up one directory If argument
   * is "/", change the cd to root If argument is absolute path, check if the path exists, then change
   * to that directory If argument is relative path, check if that path exists, then change to that
   * directory
   * 
   * @param arguments the array of arguments provided by user
   * @return true if the argument was processed and the change of directory was successful
   */
  public boolean run(String[] arguments) {
    this.cdArguments = new ArrayList<String>(Arrays.asList(arguments));
    String argument = this.cdArguments.get(0);
    if (argument.equals(this.filesys.getRoot().getName())
        || (argument.charAt(0) == ('/'))) {
      this.filesys.assignCurrent(this.filesys.getRoot());
      return true;
    }
    String[] splitArgs = argument.split("/");
    if (this.isBackwards()) {
      for (int i = 0; i < splitArgs.length; i++) {
        if (filesys.getCurrent().getParent() != null) {
          filesys.assignCurrent(this.filesys.getCurrent().getParent());
        }
      }
      return true;
    }

    if (splitArgs.length == 1) {
      if (argument.equals("..")) {
        if (filesys.getCurrent().getName()
            .equals(this.filesys.getRoot().getName())) {
          return true;
        }
        filesys.assignCurrent(this.filesys.getCurrent().getParent());
        return true;
      }

      else if (argument.equals(".")) {
        return true;
      }

      else {
        successfulPath = this.makeRelativePath(argument);
      }
    }

    else {
      if (splitArgs[0].equals(filesys.getRoot().getName())) {
        successfulPath = this.makePathFromRoot(argument);
      } else {
        successfulPath = this.makeRelativePath(argument);
      }
    }
    return successfulPath;
  }
}
