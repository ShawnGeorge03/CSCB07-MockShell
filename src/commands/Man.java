package commands;

import java.util.Arrays;
import java.util.Hashtable;

/**
 * Class man provides documentation for requested command
 */
public class Man implements CommandI {
  /**
   * Declare instance variable of Hashtable
   */
  Hashtable<String, String> my_dict;
  /**
   * Declare instance variable of ErrorHandler to handle error messages
   */
  private ErrorHandler err;

  /**
   * Constructor for Man which initializes instance variables and fills Hashtable
   */
  public Man() {
    my_dict = new Hashtable<String, String>();
    this.err = new ErrorHandler();
    setDictionary();
  }

  /**
   * Provides documentation depending on the command requested
   * 
   * @param args  the string array with all arguements provided by user
   * @param fullInput the string that contains the raw input provided by user in JShell
   * @return the documentation of the requested commands
   */
  public String run(String[] args, String fullInput) {
    if (args.length == 0 || args.length > 1 || !my_dict.containsKey(args[0])) {
      
      String param = Arrays.toString(args);
      param = param.substring(1,param.length()-1).replace(",", "").trim();
          
      return err.getError("Invalid Argument", param);
    }
    return my_dict.get(args[0]);
  }

  /**
   * Fills hashtable with all commands and their respective documentation
   */
  public void setDictionary() {

    my_dict.put("speak",
        "Command : speak" + "\n\tConverts text to audiable speech"
            + "\n\n\tParameter : Text must be inside of quotes(\" \") "
            + "\n\tto be converted to audio "
            + "\n\n\tParameter : If there was no text provided then all"
            + "\n\tinput provided next will be converted to audio, it"
            + "\n\twill only exit if the special key word QUIT is used"
            + "\n\n\tSample Output : $ speak \"Hello World\""
            + "\n\tConverts Hello World to audio and exits" + "\n\n\tSample Output : $ speak"
            + "\n\t$ What's up" + "\n\tConverts Whats up to audio"
            + "\n\t$ Hello Professor, How are you QUIT"
            + "\n\tConverts Hello Professor How are you to audio and exits ");

    my_dict.put("mkdir",
        "Command: mkdir" + "\nCreates a new directory in the current working directory"
            + "\nYou can specify a relative or absolute path where you would like the "
            + "new directory to be created");

    my_dict.put("cd", "3");
    my_dict.put("ls", "Command: ls"
        + "\nLists all the files and directories within the specified directory"
        + "\nParameters: No arguments or an absolute or relative pathname"
        + "\nIf not arguments, all files and directories within current working directory will be listed"
        + "\nIf pathname provided, all files and directories in that path will be listed");

    my_dict.put("pwd", "Command: pwd" + "\nPrints the current working directory");

    my_dict.put("pushd",
        "Command: pushd" + "\nTakes a relative or absolute path as an argument and pushes it onto"
            + "\nthe current directory stack");

    my_dict.put("popd", "Command: popd"
        + "\nPops the latest directory on the directory stack into the current working directory");

    my_dict.put("history",
        "Command : history" + "\n\tPrints out recent commands, one command per line. "
            + "\n\tIf there is a number after the commad, then the output is "
            + "\n\ttruncated by the specified numeber"
            + "\n\n\tParameter : None or a whole number (3, 7, 100 and such)"
            + "\n\n\tSample Output : $ history" + "\n\t 1. ls" + "\n\t 2. cd users " + "\n\t 3. pwd"
            + "\n\t 4. echo \"Hello World\" > C/pics/picturefile"
            + "\n\t 5. cat C/pics/picturefile.txt" + "\n\t 6. history"
            + "\n\n\tSample Output : $ history 4"
            + "\n\t 4. echo \"Hello World\" > C/pics/picturefile"
            + "\n\t 5. cat C/pics/picturefile.txt" + "\n\t 6. history" + "\n\t 7. history 4");

    my_dict.put("cat",
        "Command : cat" + "\n\tDisplays the content of File(s) in series"
            + "\n\n\tParameter : FILE(s) can be absolute or relative path(s)"
            + "\n\tParameter : It requires a file or can take a list of "
            + "\n\tFILE(s) seperated by a space" + "\n\n\tSample Output : $ cat picturefile"
            + "\n\tThis is a picture file"
            + "\n\n\tSample Output : $ cat C/pics/picturefile text C/hello"
            + "\n\tThis is a picture file" + "\n\n\n\tHello World" + "\n\n\n\tHow are you QUIT");

    my_dict.put("echo", "Command: echo"
        + "\n\tParameters: String (in quotations), and arrow or"
        + "\n\tdouble arrow followed by filename"
        + "\n\n\tIf provided without arrow and fileaname, "
        + "\n\tit simply prints out string in the console"
        + "\n\n\tIf provided with a single arrow and filename,"
        + "\n\tit overwrites that file's content with the string"
        + "\n\n\tIf provided with double arrow and filename, "
        + "\n\tit appends that file's content with the string"
        + "\n\n\tFilename can be given in the form of an absolute"
        + "\n\tpath or relative path");

    my_dict.put("man",
        "Command: man" + "\nProvides documentation on all commands within THIS Java Shell"
            + "\nProvides information such as arguments and function.");

    my_dict.put("exit", "Command : exit" + "\n\n\tCloses the current session and leaves the Shell"
        + "\n\n\tParameter : None");
  }


}
