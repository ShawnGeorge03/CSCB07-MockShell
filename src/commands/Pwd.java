package commands;

import commands.DirectoryManager;

public class Pwd extends DirectoryManager {

  public void printDirectory() {
    System.out.println(this.getCurrentPath());
  }
}
