package driver;

import java.util.ArrayList;

public class Checker {
  
  public String userInput;
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
    if(input.contains("\"")) {
      String command_name = input.substring(0, input.indexOf("\"")).trim().replaceAll("\\s+", " ");
      String temp = input.substring(input.indexOf("\""), input.length());
      return command_name + " " + temp;
    }
    else if(input.contains("'")) {
      String command_name = input.substring(0, input.indexOf("'")).trim().replaceAll("\\s+", " ");
      String temp = input.substring(input.indexOf("'"), input.length());
      return command_name + " " + temp;
    }
    return input.trim().replaceAll("\\s+", " ");
  }
  
  public void executeIfValid(String input) {
    String command_name = input.split(" ")[0];
    
    if(implemented_commands.contains(command_name)) {
      Command commandHandler = new Command();
      commandHandler.setCommand(input);
    }
    
  }
  
  public void add_command(String command_name) {
    implemented_commands.add(command_name);
  }
  
}
