package commands;

import java.util.ArrayList;
import java.util.Arrays;
import commands.DirectoryManager;
import data.FileSystem;
import data.Node;

public class Ls extends DirectoryManager implements CommandI {
  ArrayList<String> args;
  private ErrorHandler error;

  public Ls() {
    this.error = new ErrorHandler();
  }
  
  public String run(String[] arguments, String fullInput) {
    //listDirectory(args);
    
    this.args = new ArrayList<String>(Arrays.asList(arguments));
    String output = "";
	
	if (args.size() > 1) {
		return error.getError("Mulptile parameters provided", "Expecting 0 or 1 parameter");
	}
	
    if (args.size() == 0) {
      Node curr = filesys.getCurrent();
      for (int i = 0; i < curr.getList().size(); i++) {
    	System.out.println(curr.getList().get(i).getName());
    	output += curr.getList().get(i).getName() + "|";
      }
    } else {
      String[] path = {args.get(0)};
      String[] currentPath = {getCurrentPath()};

      Cd traverse = new Cd();
	      if (traverse.run(path)) {
	        Node current = FileSystem.getFileSys().getCurrent();
	
	        for (int i = 0; i < current.getList().size(); i++) {
	          System.out.println(current.getList().get(i).getName());
	          output += current.getList().get(i).getName() + "|";
	        }
	      } else {
	        return error.getError("Invalid Directory", args.get(0) + "is not a valid directory");
	      }

      Cd goBack = new Cd();
      goBack.run(currentPath);
      return output;
    }
    return null;
  }
}
