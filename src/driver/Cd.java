package driver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.*;

public class Cd extends DirectoryManager {

  ArrayList<String> cd_args;
  FileSystem filesys;
  boolean successfulPath = false;

  public Cd(String[] arguments) {
    filesys = FileSystem.getFileSys();
    this.cd_args = new ArrayList<String>(Arrays.asList(arguments));
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

  public boolean run() {
    String argument = this.cd_args.get(0);
    if (argument.equals(this.filesys.getRoot().name) || (argument.charAt(0) == ('/'))) {
      this.filesys.assignCurrent(this.filesys.getRoot());
      return true;
    }
    String[] split_args = argument.split("/");
    if (this.isBackwards()) {
      for (int i = 0; i < split_args.length; i++) {
        if (filesys.getCurrent().parent != null) {
          filesys.assignCurrent(this.filesys.getCurrent().parent);
        }
      }
      return true;
    }

    if (split_args.length == 1) {
      if (argument.equals("..")) {
        if (filesys.getCurrent().name.equals(this.filesys.getRoot().name)) {
          return true;
        }
        filesys.assignCurrent(this.filesys.getCurrent().parent);
      }

      else if (argument.equals(".")) {
        System.out.println("PWD: " + this.getCurrentPath());
        return true;
      }

      else {
        this.makeRelativePath(argument);
        System.out.println(filesys.current.name);
      }
    }

    else {
      if (split_args[0].equals(filesys.getRoot().name)) {
        successfulPath = this.makePathFromRoot(argument);
      } else {
        successfulPath = this.makeRelativePath(argument);
      }
    }
    System.out.println("PWD: " + this.getCurrentPath());
    return successfulPath;
  }
}
