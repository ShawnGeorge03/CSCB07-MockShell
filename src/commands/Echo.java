package commands;

/**
 * Class Echo is responsible for printing to the shell and redirecting the input to perform the 
 * appropriate task
 */
public class Echo extends FileManager implements CommandI {

  /** Declare instance variable of String to hold the arguments */
  private String argument;
  private int num_arrow;
  
  /** Declare instance variable of String to hold the output that will be returned */
  String output = "";

  /**
   * Main run method that checks if the user had inputted any arguments to the command.
   * It splices the input so that it can send parsed data to the appropriate implementation of echo.
   * Calls the execute method that will perform the required task for the appropriate scenario.
   * Returns a String that will either contain an error message if there was an error or return null
   * 
   * @param args  the String array of arguments provided by user (split from a whitespace)
   * @return String  will either be null if there were no errors or an appropriate error message 
   */
  public String run(String[] args, String fullInput, boolean val) {
    if(args.length == 0) {
      return getErrorHandler().getError("No parameters provided", "");
    }
    String[] sliced = fullInput.split(" ");

    argument = fix_argument(sliced);
    execute(sliced, fullInput);
    
    return output;
  }

  private boolean hasQuotations(String fullInput) {
    if (fullInput.contains("\"")) {
      int num = fullInput.length() - fullInput.replaceAll("\"", "").length();
      if (num % 2 == 0)
        return true;
    }
    return false;
  }

  private int count_arrows(String parsedInput) {
    return parsedInput.length() - parsedInput.replaceAll(">", "").length();
  }

  private String fix_argument(String[] spliced_input) {
    String args = "";

    for (int i = 1; i < spliced_input.length; i++) {
      args += spliced_input[i] + " ";
    }

    return args.substring(0, args.length() - 1);
  }

  /**
   * Method that is responsible for printing the required arguments to the shell/console.
   * 
   * @param args  the string array of arguments
   */
  public void printToConsole(String[] args) {
    for (int i = 1; i < args.length; i++) {
      output += args[i] + " ";
    }

    output = output.substring(0, output.length() - 1).replaceAll("\"", "");
  }

  private void execute(String[] args, String fullInput) {
    String fileContents = "";
    if (hasQuotations(fullInput)) {
      fileContents = fullInput.substring(fullInput.indexOf("\"") + 1, fullInput.lastIndexOf("\""));
      String fileName = argument.substring(argument.lastIndexOf(">") + 1, argument.length());
      fileName = fileName.replaceAll("^\\s+", "");
            
      num_arrow = count_arrows(fullInput.substring(fullInput.lastIndexOf("\"")+1, fullInput.length()));
      
      if (num_arrow == 0 && args.length > 1)
        printToConsole(args);

      else if (num_arrow == 1) {
        if (argument.split(">").length == 2 && !argument.split(">")[0].equals("")) {
          EchoOverwrite overwrite_exe = new EchoOverwrite();
          overwrite_exe.execute(fileContents, fileName);
          output = null;
          return;
        } else
          output = this.getErrorHandler().getError("Invalid File", fullInput);
          return;
        }

      else if (num_arrow == 2) {
        if (argument.split(">>").length == 2 && !argument.split(">>")[0].equals("")) {
          EchoAppend append_exe = new EchoAppend();
          append_exe.execute(fileContents, fileName);
          output = null;
          return;
        } else
          output = this.getErrorHandler().getError("Invalid File", fullInput);
          return;
      }

      else
        output = this.getErrorHandler().getError("Invalid Argument", fullInput);
        return;
    }
    else
      output = this.getErrorHandler().getError("Missing Quotes", fullInput);
      return;
  }
}
