package commands;

public class Exit implements CommandI{

  @Override
  public String run(String[] args, String fullInput) {
    System.exit(0);
    return null;
  }

}
