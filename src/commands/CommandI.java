package commands;

/**
 * Interface CommandI is responsible for providing a comman method to call any 
 * class containing that certain command
 */  

interface CommandI {

 /**
  * Runs the command based on the implementation of this method in the 
  * respective class
  * 
  * @param args the string array of arguments
  * @param fullInput the full line of input that the user gives into JShell
  * @param val stores a boolean value
  *
  * @return any error message or valid output for user input
  */  
  public String run(String args[], String fullInput, boolean val);

}
