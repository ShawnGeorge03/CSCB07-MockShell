
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
package driver;

import java.util.Scanner;
import commands.Checker;
import data.FileSystem;

/**
 * Class JShell is the main driver program where the user inputs commands
 */
public class JShell {
  /**
   * Main method which handles the main loop for shell commands
   * @param args
   */
  public static void main(String[] args) {

    //For running TestCases.java set to true
    boolean testing = false;

    TestCases tester = new TestCases();


    if (testing) {
      tester.runTestCases();
    } else {
      Checker parser = new Checker();
      FileSystem fs = FileSystem.getFileSys();
      Scanner sc = new Scanner(System.in);

      boolean running = true;

      while (running) {
        System.out.print("$");
        String input = sc.nextLine();
        fs.getCommandLog().add(input);
        parser.parseInput(input);
      }
      sc.close();

    }
  }
}
