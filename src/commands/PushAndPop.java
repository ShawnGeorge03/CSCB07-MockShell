package commands;

import commands.DirectoryManager;
import data.FileSystem;

public class PushAndPop extends DirectoryManager {
  private ErrorHandler error;

  public PushAndPop() {
    this.error = new ErrorHandler();
  }

  public void pushPath(String[] args) {
    if (args.length == 0) {
      error.getError("Invalid Argument",  Integer.toString(args.length) + " arguments, expecting 1 argument");
      return;
    }
    
    if (FileSystem.getStack().isEmpty()) {
      FileSystem.getStack().push(getCurrentPath());
    }
    FileSystem.getStack().push(args[0]);
    Cd newWorkingDirectory = new Cd();
    newWorkingDirectory.run(args);
  }

  public void pop() {
    if (FileSystem.getStack().size() == 0) {
      System.out.println("stack is empty");
      return;
    }
    FileSystem.getStack().pop();
    String[] path = {FileSystem.getStack().peek()};
    FileSystem.getStack().remove();
    Cd newWorkingDirectory = new Cd();
    newWorkingDirectory.run(path);
  }


}
