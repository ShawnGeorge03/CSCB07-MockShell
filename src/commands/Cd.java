package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.*;
import data.FileSystem;

public class Cd extends DirectoryManager implements CommandI {

  ArrayList<String> cd_args;
  FileSystem filesys;
  boolean successfulPath = false;
  ErrorHandler error;
  String err_output;

  public Cd() {
    filesys = FileSystem.getFileSys();
    error = new ErrorHandler();
    
  }
  
  public String run(String[] args, String fullInput) {
    if (args.length == 0) {
      err_output = error.getError("No parameters provided", "");
      return err_output;
    }
    run(args);
    return null;
  }

  public boolean isBackwards() {
    String cur = this.cd_args.get(0);
    if (Pattern.matches("(../)+", cur) || (Pattern.matches("(../..)+", cur))) {
      return true;
    }
    return false;
  }

  public void printArr(String[] arr) {
    for (int i = 0; i < arr.length; i++) {
      System.out.println(arr[i]);
    }
  }

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
  
  
  public static void main(String[] args) {
    
  }
}
