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
  
  public void printLastXCommands(int x) {
    for(int i = x; i < CommandLog.size(); i++) {
      System.out.println((i+1)+"i " + CommandLog.get(i));
    }
  }
  

}
