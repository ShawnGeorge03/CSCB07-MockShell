package commands;

import java.util.ArrayList;
import java.util.Arrays;
import commands.DirectoryManager;
import data.FileSystem;
import data.Node;

/**
 * Class Ls handles viewing the child nodes of requested directory
 */
public class Ls extends DirectoryManager implements CommandI {
  /**
   * Declare instance variable of ArrayList to contain all arguments
   */
  ArrayList<String> args;
  /**
   * Declare instance variable of ErrorHandler to handle error messages
   */
  private ErrorHandler error;

  /**
   * Constructor for Ls to initialize error
   */
  public Ls() {
    this.error = new ErrorHandler();
  }

  /**
   * Displays all of the given directory's child nodes
   * 
   * @param args the string array that contains the arguments
   * @param fullInput the string that contains the raw input that the user provides to JShell
   * @return null always
   */
  public String run(String[] arguments, String fullInput, boolean val) {
    this.args = new ArrayList<String>(Arrays.asList(arguments));
    String output = "";

    if (args.size() == 0) {
      Node curr = filesys.getCurrent();
      for (int i = 0; i < curr.getList().size(); i++) {
        System.out.println(curr.getList().get(i).getName());
      }
    } else {
      for (int i = 0; i < args.size(); i++) {
        String[] path = {args.get(i)};
        String[] currentPath = {getCurrentPath()};

        Cd traverse = new Cd();
        if (traverse.run(path)) {
          Node current = FileSystem.getFileSys().getCurrent();
          System.out.println("Path: " + path[0]);
          for (int j = 0; j < current.getList().size(); j++) {
            System.out.println(current.getList().get(j).getName());
          }
        } else {
          return error.getError("Invalid Directory",
              args.get(i) + " is not a valid directory");
        }

        Cd goBack = new Cd();
        goBack.run(currentPath);
        System.out.println();
      }
    }
    return null;
  }
}
