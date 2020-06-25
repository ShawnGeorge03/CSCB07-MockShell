package driver;

import java.util.ArrayList;

public class History {

  private ArrayList<String> CommandLog;
  
  public History() {
    this.CommandLog = new ArrayList<String>();
  }
  
  public void addCommands(String Command) {
    CommandLog.add(Command);
  }
  
  public int getCommandLogSize() {
    return CommandLog.size();
  }
  
  public void printLastXCommands(int x) {
    if(x <= 0) { 
      System.out.println("IndexOutOfBoundError");
      return;
    }
    
    for(int i = CommandLog.size() - x; i < CommandLog.size(); i++) {
      System.out.println((i+1)+". " + CommandLog.get(i));
    }
  }
  
  

}
