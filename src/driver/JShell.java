
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

    Checker parser = new Checker();
    History history = new History();
    Scanner sc = new Scanner(System.in);

    boolean running = true;

    while (running) {
      System.out.print("$");
      String input = sc.nextLine();
      history.addCommands(input);
      parser.parseInput(input);
    }
    sc.close();
  }
}
