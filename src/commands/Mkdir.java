package commands;

import java.util.ArrayList;
import java.util.Arrays;
import commands.DirectoryManager;
import data.FileSystem;
import data.Node;


public class Mkdir extends DirectoryManager implements CommandI {
  ArrayList<String> args;
  private ErrorHandler error;

  public Mkdir() {
    this.error = new ErrorHandler();
  }
  
  public String run(String[] args, String fullInput) {
    MakeDirectory(args);
    return null;
  }
  
  

  public void MakeDirectory(String[] arguments) {
	 this.args = new ArrayList<String>(Arrays.asList(arguments));
	  
    if (checkValidArgs()) {
      if (checkPath()) {
        String[] currentPath = {getCurrentPath()};
        String[] newArgs = {args.get(0).substring(0, args.get(0).lastIndexOf('/'))};
        

        Cd newpath = new Cd();
        if (newpath.run(newArgs)) {
          Node newNode = new Node();
          newNode.setContent(null);
          newNode.setDir(true);
          newNode.setName(args.get(0).substring(args.get(0).lastIndexOf('/') + 1));

          for (int i = 0; i < filesys.getCurrent().getList().size(); i++) {
            if (filesys.getCurrent().getList().get(i).getName().equals(newNode.getName())) {
              Cd goBack = new Cd();
              goBack.run(currentPath);
              error.getError("Same Directory", newArgs[0] + " already exists");
              return;
            }
          }

          filesys.addToDirectory(newNode);
        } else {
          error.getError("Invalid Directory", newArgs[0] + " is not a valid directory");
        }

        Cd goBack = new Cd();
        goBack.run(currentPath);
        return;
      } else {
        Node newNode = new Node();
        newNode.setContent(null);
        newNode.setDir(true);
        newNode.setName(args.get(0));

        for (int i = 0; i < filesys.getCurrent().getList().size(); i++) {
          if (filesys.getCurrent().getList().get(i).getName().equals(newNode.getName())) {
        	error.getError("Same Directory", newNode.getName() + " already exists");
            return;
          }
        }

        filesys.addToDirectory(newNode);
        return;
      }
    } else {
    	error.getError("Invalid Arguments", "Expecting 1 Argument only");
    }
  }

  private boolean checkValidArgs() {
    return args.size() == 1;
  }

  private boolean checkPath() {
    return args.get(0).contains("/");
  }
}
