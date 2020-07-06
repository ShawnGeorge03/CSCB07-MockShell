package commands;

import java.util.ArrayList;
import java.util.Arrays;
import commands.DirectoryManager;
import data.FileSystem;
import data.Node;

public class Ls extends DirectoryManager {
  ArrayList<String> args;
  private ErrorHandler error;

  public Ls(String[] args) {
    this.error = new ErrorHandler();
    this.args = new ArrayList<String>(Arrays.asList(args));
  }

  public void listDirectory() {
    if (args.size() == 0) {
      Node curr = filesys.getCurrent();
      for (int i = 0; i < curr.getList().size(); i++) {
        if (curr.getList().get(i).isDir()) {
          System.out.println(curr.getList().get(i).getName());
        } else {
          // System.out.println(curr.list.get(i).name + ".txt");
          System.out.println(curr.getList().get(i).getName());
        }
      }
    } else {
      String[] path = {args.get(0)};
      String[] currentPath = {getCurrentPath()};

      Cd traverse = new Cd(path);
      if (traverse.run()) {
        Node current = FileSystem.getFileSys().getCurrent();

        for (int i = 0; i < current.getList().size(); i++) {
          System.out.println(current.getList().get(i).getName());
        }
      } else {
        error.getError("Invalid Directory");
      }

      Cd goBack = new Cd(currentPath);
      goBack.run();
      return;
    }
  }
}
