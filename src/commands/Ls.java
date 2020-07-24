// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: patelt26
// UT Student #: 1005904103
// Author: Shawn Santhoshgeorge
//
// Student2:
// UTORID user_name: shaiskan
// UT Student #: 1006243940
// Author: Keshavaa Shaiskandan
//
// Student3:
// UTORID user_name: patelt26
// UT Student #: 1005904103
// Author: Tirth Patel
//
// Student4:
// UTORID user_name: pate1101
// UT Student #: 1006315765
// Author: Abhay Patel
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package commands;

import java.util.Arrays;
import java.util.ArrayList;
import data.FileSystemI;
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
   * @param args  the string array that contains the arguments
   * @param fullInput  the string that contains the raw input that the user provides to JShell
   * @return null always
   */
  public String run(FileSystemI filesys, String[] arguments, String fullInput, boolean val) {
	this.args = new ArrayList<String>(Arrays.asList(arguments));
	if (args.size() == 0){
		return unrecursiveMode(filesys);
	}
	 if (!args.get(0).equals("-R")){
		 return unrecursiveMode(filesys);
	 } else{
		 return recursiveMode(filesys);
	 }

    // if (args.get(0).equals("-R") != true) {
    // 	unrecursiveMode();
    // }else {
    // 	recursiveMode();
	// }
 }


public String unrecursiveMode(FileSystemI filesys) {
	if (args.size() == 0) {
	      Node curr = filesys.getCurrent();
	      for (int i = 0; i < curr.getList().size(); i++) {
	        System.out.println(curr.getList().get(i).getName());
	      }
	    } else {
	      for (int i = 0; i < args.size(); i++) {
	        String[] path = {args.get(i)};
	        String[] currentPath = {getCurrentPath(filesys)};

	        Cd traverse = new Cd();
	        if (traverse.run(path, filesys)) {
	          Node current = filesys.getCurrent();
	          System.out.println("Path: " + getCurrentPath(filesys));
	          for (int j = 0; j < current.getList().size(); j++) {
	            System.out.println(current.getList().get(j).getName());
	          }
	        } else {
	          return error.getError("Invalid Directory",
	              args.get(i) + " is not a valid directory");
	        }

	        Cd goBack = new Cd();
	        goBack.run(currentPath, filesys);
	        System.out.println();
	      }
	    }
	    return null;
	}


	public String recursiveMode(FileSystemI filesys) {
		Cd traverse = new Cd();
		String[] currentPath = {getCurrentPath(filesys)};
		if (args.size() == 1){
			listDirectory(filesys.getRoot(), filesys);
		}else{
			for (int i = 1; i < args.size(); i++){
				String[] path = {args.get(i)};
				if (traverse.run(path, filesys)){
					listDirectory(filesys.getCurrent(), filesys);
				}
				traverse.run(currentPath, filesys);
			}
		}
		traverse.run(currentPath, filesys);
		return null;
	}

	public String listDirectory(Node root, FileSystemI filesys){
		if (!root.isDir()){
			return null;
		}
		filesys.assignCurrent(root);
		System.out.println("Path: " + getCurrentPath(filesys));
		for (int i = 0; i < root.getList().size(); i++){
			System.out.println(root.getList().get(i).getName());
		}
		System.out.println();
		for (int i = 0; i < root.getList().size(); i++){
			listDirectory(root.getList().get(i), filesys);
		}
		return null;
	}
}
