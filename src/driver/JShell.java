
// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name:
// UT Student #:
// Author:
//
// Student2:
// UTORID user_name:
// UT Student #:
// Author:
//
// Student3:
// UTORID user_name:
// UT Student #:
// Author:
//
// Student4:
// UTORID user_name:
// UT Student #:
// Author:
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package driver;
import java.util.*;

public class JShell {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
	  
      Checker parser = new Checker();
	  
      //FileSystem instance = null;
	  
	  //FileSystem newinstance = null;
	  
	  //newinstance = newinstance.getFileSys();
	  
	  //Node newro = newinstance.getRoot();
	  
	  boolean running = true;
	  
	  while(running) {
	    Scanner sc = new Scanner(System.in);
	    System.out.print(">>>");
	    String input = sc.nextLine(); 
	    if (input.equals("exit")) {
	      running = false;
	    }
	    else {
	      input = parser.parseInput(input);
	      if(parser.isValidCommand(input))
	      {
	        //do stuff
	      }
	      System.out.println(input);
	    }
	    
	  }
	  
  }

}
