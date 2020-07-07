package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.*;
import data.FileSystem;

public class Cd extends DirectoryManager implements CommandI {

  ArrayList<String> cd_args;
  boolean successfulPath = false;
  String err_output;
  /**
   * Declare instance of FileSystem
   */
  FileSystem filesys;
  /**
   * Declare instance of ErrorHandler
   */
  ErrorHandler error;

  /**
   * Constructor for method Cd which changes directories within the FileSystem
   */
  public Cd() {
    filesys = FileSystem.getFileSys();
    error = new ErrorHandler();

  }

  /**
   * Starting run method which checks if arguments were given, then passes it to another run method
   * which processes the command
   * 
   * @param args This is a string array of arguments
   * @param fullInput This is the full line of input that the user gives into JShell
   * @return string Returns any error messages if there are any
   */
  public String run(String[] args, String fullInput) {
    if (args.length == 0) {
      err_output = error.getError("No parameters provided", "");
      return err_output;
    }
    run(args);
    return null;
  }

  /**
   * Returns true if the argument follows the pattern of ../ in which each pair of periods dictates
   * how many directories to go up
   * 
   * @return True if argument matches pattern, false otherwise
   */
  public boolean isBackwards() {
    String cur = this.cd_args.get(0);
    if (Pattern.matches("(../)+", cur) || (Pattern.matches("(../..)+", cur))) {
      return true;
    }
    return false;
  }

  /**
   * Main run method that executes the performance of changing directories based on what argument is
   * given. If argument is ".", nothing happens If argument is "..", go up one directory If argument
   * is "/", change the cd to root If argument is absolute path, check if the path exists, then
   * change to that directory If argument is relative path, check if that path exists, then change
   * to that directory
   * 
   * @param arguments Array of arguments provided by user
   * @return True if the argument was processed and the change of directory was successful
   */
  public boolean run(String[] arguments) {
    this.cd_args = new ArrayList<String>(Arrays.asList(arguments));
    String argument = this.cd_args.get(0);
    if (argument.equals(this.filesys.getRoot().getName()) || (argument.charAt(0) == ('/'))) {
      this.filesys.assignCurrent(this.filesys.getRoot());
      return true;
    }
    String[] split_args = argument.split("/");
    if (this.isBackwards()) {
      for (int i = 0; i < split_args.length; i++) {
        if (filesys.getCurrent().getParent() != null) {
          filesys.assignCurrent(this.filesys.getCurrent().getParent());
        }
      }
      return true;
    }

    if (split_args.length == 1) {
      if (argument.equals("..")) {
        if (filesys.getCurrent().getName().equals(this.filesys.getRoot().getName())) {
          return true;
        }
        filesys.assignCurrent(this.filesys.getCurrent().getParent());
      }

      else if (argument.equals(".")) {
        return true;
      }

      else {
        successfulPath = this.makeRelativePath(argument);
      }
    }

    else {
      if (split_args[0].equals(filesys.getRoot().getName())) {
        successfulPath = this.makePathFromRoot(argument);
      } else {
        successfulPath = this.makeRelativePath(argument);
      }
    }
    return successfulPath;
  }


  // public static void main(String[] args) {
  // Cd test = new Cd();
  // test.run("/");
  // System.out.println(test.getCurrentPath());
  // test.run(".");
  // System.out.println(test.getCurrentPath());
  // test.run("..");
  // System.out.println(test.getCurrentPath());
  //
  //
  // }
}
