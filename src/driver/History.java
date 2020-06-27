package driver;

public class History {
  
  public void addCommands(String Command) {
    FileSystem.CommandLog.add(Command);
  }
  
  public int getCommandLogSize() {
    return FileSystem.CommandLog.size();
  }
  
  public void printLastXCommands(int x) {
    if(x <= 0) { 
      System.out.println("IndexOutOfBoundError");
      return;
    }
    
    for(int i = FileSystem.CommandLog.size() - x; i < FileSystem.CommandLog.size(); i++) {
      if(i < 0) continue;
      System.out.println((i+1)+". " + FileSystem.CommandLog.get(i));
    }
  }
  
  

}
