package driver;

import java.util.Scanner;

public class Checker {
  
  public String userInput;
  //private ErrorMessage error;
  //private Command valid_commands;
  
  /*
   * Idea:
   * Make a linked list, or use an ArrayList of commands that are valid so we can check if the input
   * given by user match any of the commands that have been implemented
   * 
   */
  
  public Checker() {
    this.userInput = "";
    //make ErrorMessage null or something
  }
  
  public String parseInput(String input) {
    return input.trim().replaceAll("\\s+", " ");
  }
  
  //work on implementing after the command class is done
  public boolean isValidCommand(String input) {
    return true;
  }
  
  //Main method for testing code
  public static void main(String[] args) {
    Checker test = new Checker();
    Scanner input = new Scanner(System.in);
    while(!test.userInput.equals("exit")) {
      System.out.print(">>>");
      test.userInput = input.nextLine().trim().replaceAll("\\s+", " ");
      System.out.println(test.userInput);
    }
  }

}
