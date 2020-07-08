package commands;

import java.util.ArrayList;
import java.util.Arrays;
import commands.DirectoryManager;
import data.FileSystem;
import data.Node;

/**
 * Class Mkdir handles making directories anywhere in the filesystem
 */
public class Mkdir extends DirectoryManager implements CommandI {
  /**
   * Declare instance variable of ArrayList to hold all arguments
   */
  ArrayList<String> args;
  /**
   * Declare instance variable of ErrorHandler to handle error messages
   */
  private ErrorHandler error;

  /**
   * Constructor of Mkdir to initialize error
   */
  public Mkdir() {
    this.error = new ErrorHandler();
  }

  /**
   * Generic run method to call on method that does the work of creating directories
   * 
   * @param args the string array of all arguments
   * @param fullInput the string of the entire raw input provided by user in JShell
   * @return null always
   */
  public String run(String[] args, String fullInput, boolean val) {
    String output = MakeDirectory(args);
    return output;
  }


  /**
   * Makes directories at locations in filesystem based on the path given
   * 
   * @param arguments the string array of all arguments provided
   */
  public String MakeDirectory(String[] arguments) {
    this.args = new ArrayList<String>(Arrays.asList(arguments));

    if (checkValidArgs()) {

      if (checkPath()) {
        String[] currentPath = {getCurrentPath()};
        String[] newArgs =
            {args.get(0).substring(0, args.get(0).lastIndexOf('/'))};

        if (!isValidDirectoryName(
            args.get(0).substring(args.get(0).lastIndexOf('/') + 1))) {
          return error.getError("Invalid Directory",
              args.get(0).substring(args.get(0).lastIndexOf('/') + 1)
                  + " is not a valid directory name");
        }

        Cd newpath = new Cd();
        if (newpath.run(newArgs)) {
          Node newNode = new Node();
          newNode.setContent(null);
          newNode.setDir(true);
          newNode
              .setName(args.get(0).substring(args.get(0).lastIndexOf('/') + 1));

          for (int i = 0; i < filesys.getCurrent().getList().size(); i++) {
            if (filesys.getCurrent().getList().get(i).getName()
                .equals(newNode.getName())) {
              Cd goBack = new Cd();
              goBack.run(currentPath);
              return error.getError("Same Directory",
                  newArgs[0] + " already exists");
            }
          }

          filesys.addToDirectory(newNode);
        } else {
          return error.getError("Invalid Directory",
              newArgs[0] + " is not a valid directory");
        }

        Cd goBack = new Cd();
        goBack.run(currentPath);
        return null;
      } else {
        if (!isValidDirectoryName(args.get(0))) {
          return error.getError("Invalid Directory",
              args.get(0) + " is not a valid directory name");
        }

        Node newNode = new Node();
        newNode.setContent(null);
        newNode.setDir(true);
        newNode.setName(args.get(0));

        for (int i = 0; i < filesys.getCurrent().getList().size(); i++) {
          if (filesys.getCurrent().getList().get(i).getName()
              .equals(newNode.getName())) {
            return error.getError("Same Directory",
                newNode.getName() + " already exists");
          }
        }

        filesys.addToDirectory(newNode);
        return null;
      }
    } else {
      return error.getError("Invalid Argument", "Expecting 1 Argument only");
    }
  }

  private boolean checkValidArgs() {
    return args.size() == 1;
  }

  private boolean checkPath() {
    return args.get(0).contains("/");
  }
}
