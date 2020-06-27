package driver;

public class Echo extends FileManager{
  
  private String argument;  

  public void compile_arguments(String[] input_arguments) {

    //echo hello world > test -> helloworld>test
    //echo hello world >test  -> helloworld>test
    //echo hello world> test  -> helloworld>test
    //echo hello world>test   -> helloworld>test
    argument = fix_argument(input_arguments);
    
    int num_arrow = count_arrows(argument);
    
    run(input_arguments, num_arrow);
  }
  
  public int count_arrows(String parsedInput) {
    return parsedInput.length() - parsedInput.replaceAll(">", "").length();
  }
  
  public String fix_argument(String[] spliced_input) {
    String args = "";
    
    for(int i = 0; i < spliced_input.length; i++){
      args += spliced_input[i];
    }
        
    return args;
  }
  
  public void printToConsole(String[] args) {
    String output = "";
    
    for(int i = 0; i < args.length; i++) {
      output += args[i] + " ";
    }
    output = output.substring(0,output.length()-1);
    System.out.println(output.replaceAll("\"", ""));
  }
  
  public void run(String[] args, int num_arrow) {
    if(num_arrow == 0 && args.length > 0) {
      printToConsole(args);
    }
    
    else if(num_arrow == 1) {      
      if(argument.split(">").length == 2 && !argument.split(">")[0].equals("")) {
        EchoOverwrite overwrite_exe = new EchoOverwrite();
        overwrite_exe.execute(args);
      }
      else {
        System.out.println("Error");//throw an error (not enough inputs/either before > or after)
      }
    }
    
    else if(num_arrow == 2) {
      if(argument.split(">>").length == 2 && !argument.split(">>")[0].equals("")) {
        EchoAppend append_exe = new EchoAppend();
        append_exe.execute(args);
      }
      else {
        System.out.println("Error");//throw an error (not enough inputs/either before > or after)
      }
    }
    
    else {
      System.out.println("Error");//throw an error (only echo was inputted)
    }
  
  }
 
}
