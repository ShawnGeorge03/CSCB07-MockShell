package commands;

/**
 * Class Exit handles exiting the JShel
 */
public class Exit implements CommandI {

  /**
   * Ends program
   * 
   * @param args the string array with all arguments
   * @param fullInput the string that contains the raw input provided to JShell
   * @return null no matter what
   */
  @Override
  public String run(String[] args, String fullInput, boolean val) {
    System.exit(0);
    return null;
  }

}
