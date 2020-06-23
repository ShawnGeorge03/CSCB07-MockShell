package driver;

import java.util.ArrayList;
import java.util.Scanner;

public class Checker {
  
  public String userInput;
  //temp arraylist
  private ArrayList<String> implemented_commands = new ArrayList<String>();
  
  public Checker() {
    this.userInput = "";
    fill_commands();
  }
  
  private void fill_commands() {
    this.implemented_commands.add("ls");
    this.implemented_commands.add("mkdir");
    this.implemented_commands.add("exit");
    this.implemented_commands.add("history");
    this.implemented_commands.add("speak");
    this.implemented_commands.add("pwd");
    this.implemented_commands.add("cd");
    this.implemented_commands.add("cat");
    this.implemented_commands.add("mv");
    this.implemented_commands.add("pushd");
    this.implemented_commands.add("popd");
    this.implemented_commands.add("echo");
    this.implemented_commands.add("man");
  }
  
  public String parseInput(String input) {
    return input.trim().replaceAll("\\s+", " ");
  }
  
  public boolean isValidCommand(String input) {
    String command_name = input.split(" ")[0];
    
    if(implemented_commands.contains(command_name)) return true;
    
    return false;
  }
  
  public void add_command(String command_name) {
    implemented_commands.add(command_name);
  }
  
  //Main method for testing code
  public static void main(String[] args) {
    Checker test = new Checker();
    Scanner input = new Scanner(System.in);
    String u_input;
    while(true) {
      System.out.print(">>>");
      u_input = input.nextLine().trim().replaceAll("\\s+", " ");
      if(test.isValidCommand(u_input))
      {
        System.out.println("VALID");
        if(u_input.equals("exit")) break;
        
      }
      else 
      {
        System.out.println("THIS IS NOT A VALID COMMAND");
      }
    }
  }

}
