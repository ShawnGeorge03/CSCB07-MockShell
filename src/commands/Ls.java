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
   * Calls the listDirectory method which does the work of displaying all its child nodes
   * 
   * @param args  the string array that contains the arguments
   * @param fullInput  the string that contains the raw input that the user provides to JShell
   * @return null always
   */
  public String run(String[] args, String fullInput) {
    listDirectory(args);
    return null;
  }
  
  /**
   * Prints out all child nodes of given directory
   * 
   * @param arguments  the arguments that the user gives
   */
  public void listDirectory(String[] arguments) {
	this.args = new ArrayList<String>(Arrays.asList(arguments));
	
	if (args.size() > 1) {
		System.out.println(error.getError("Mulptile parameters provided", "Expecting 0 or 1 parameter"));
	}
	
    if (args.size() == 0) {
      Node curr = filesys.getCurrent();
      for (int i = 0; i < curr.getList().size(); i++) {
        if (curr.getList().get(i).isDir()) {
          System.out.println(curr.getList().get(i).getName());
        } else {
          System.out.println(curr.getList().get(i).getName());
        }
      }
    } else {
      String[] path = {args.get(0)};
      String[] currentPath = {getCurrentPath()};

      Cd traverse = new Cd();
      if (traverse.run(path)) {
        Node current = FileSystem.getFileSys().getCurrent();

        for (int i = 0; i < current.getList().size(); i++) {
          System.out.println(current.getList().get(i).getName());
        }
      } else {
        System.out.println(error.getError("Invalid Directory", args.get(0) + "is not a valid directory"));
      }

      Cd goBack = new Cd();
      goBack.run(currentPath);
      return;
    }
  }
}
