package commands;

public class Echo extends FileManager implements CommandI {

  private String argument;
  private int num_arrow;
  String output = "";


  public String run(String[] args, String fullInput) {
    if(args.length == 0) {
      return getErrorHandler().getError("No parameters provided", "");
    }
    String[] sliced = fullInput.split(" ");

    argument = fix_argument(sliced);
    execute(sliced, fullInput);
    
    return output;
  }

  public boolean hasQuotations(String fullInput) {
    if (fullInput.contains("\"")) {
      int num = fullInput.length() - fullInput.replaceAll("\"", "").length();
      if (num % 2 == 0)
        return true;
    }
    return false;
  }

  public int count_arrows(String parsedInput) {
    return parsedInput.length() - parsedInput.replaceAll(">", "").length();
  }

  public String fix_argument(String[] spliced_input) {
    String args = "";

    for (int i = 1; i < spliced_input.length; i++) {
      args += spliced_input[i] + " ";
    }

    return args.substring(0, args.length() - 1);
  }

  public void printToConsole(String[] args) {
    for (int i = 1; i < args.length; i++) {
      output += args[i] + " ";
    }

    output = output.substring(0, output.length() - 1).replaceAll("\"", "");
  }

  public void execute(String[] args, String fullInput) {
    
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
        } else
          output = this.getErrorHandler().getError("No parameters provided", fullInput);
          return;
        }

      else if (num_arrow == 2) {
        if (argument.split(">>").length == 2 && !argument.split(">>")[0].equals("")) {
          EchoAppend append_exe = new EchoAppend();
          append_exe.execute(fileContents, fileName);
        } else
          output = this.getErrorHandler().getError("No parameters provided", fullInput);
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
