package commands;

import java.util.Hashtable;

public class Man implements CommandI{
	Hashtable<String, String> my_dict;
	
	public Man() {
	  my_dict = new Hashtable<String, String>();
      setDictionary();
	}
	
	
	  public void run(String[] args, String fullInput) {
	    printDocumentation(args);
	  }
	
	public void printDocumentation(String[] userInput){  
	  if(userInput.length == 0 || userInput.length > 1 
	      || !my_dict.containsKey(userInput[0])){
	    System.out.println("Invalid Argument(s)");
	  }else {
	    System.out.println(my_dict.get(userInput[0]));
	  }
	}
	
	public void setDictionary() {
	  
      my_dict.put("speak", "Command : speak"
          + "\n\tConverts text to audiable speech"
          + "\n\n\tParameter : Text must be inside of quotes(\" \") "
          + "\n\tto be converted to audio "
          + "\n\n\tParameter : If there was no text provided then all"
          + "\n\tinput provided next will be converted to audio, it"
          + "\n\twill only exit if the special key word QUIT is used"
          + "\n\n\tSample Output : $ speak \"Hello World\""
          + "\n\tConverts Hello World to audio and exits"
          + "\n\n\tSample Output : $ speak"
          + "\n\t$ What's up"
          + "\n\tConverts Whats up to audio"
          + "\n\t$ Hello Professor, How are you QUIT"
          + "\n\tConverts Hello Professor How are you to audio and exits ");
      
      my_dict.put("mkdir", "Command: mkdir"
      		+ "\nCreates a new directory in the current working directory"
      		+ "\nYou can specify a relative or absolute path where you would like the "
      		+ "new directory to be created");
      
      my_dict.put("cd", "3");
      my_dict.put("ls", "Command: ls"
      		+ "\nLists all the files and directories within the specified directory"
      		+ "\nParameters: No arguments or an absolute or relative pathname"
      		+ "\nIf not arguments, all files and directories within current working directory will be listed"
      		+ "\nIf pathname provided, all files and directories in that path will be listed");
      
      my_dict.put("pwd", "Command: pwd"
      		+ "\nPrints the current working directory");
      
      my_dict.put("pushd", "Command: pushd"
      		+ "\nTakes a relative or absolute path as an argument and pushes it onto"
      		+ "\nthe current directory stack");
      
      my_dict.put("popd", "Command: popd"
      		+ "\nPops the latest directory on the directory stack into the current working directory");
      
      my_dict.put("history", "Command : history"
          + "\n\tPrints out recent commands, one command per line. "
          + "\n\tIf there is a number after the commad, then the output is "
          + "\n\ttruncated by the specified numeber"
          + "\n\n\tParameter : None or a whole number (3, 7, 100 and such)"
          + "\n\n\tSample Output : $ history"
          + "\n\t 1. ls"
          + "\n\t 2. cd users "
          + "\n\t 3. pwd"
          + "\n\t 4. echo \"Hello World\" > C/pics/picturefile"
          + "\n\t 5. cat C/pics/picturefile.txt"
          + "\n\t 6. history"
          + "\n\n\tSample Output : $ history 4"
          + "\n\t 4. echo \"Hello World\" > C/pics/picturefile"
          + "\n\t 5. cat C/pics/picturefile.txt"
          + "\n\t 6. history"
          + "\n\t 7. history 4");
      
      my_dict.put("cat", "Command : cat"
          + "\n\tDisplays the content of File(s) in series"
          + "\n\n\tParameter : FILE(s) can be absolute or relative path(s)"
          + "\n\tParameter : It requires a file or can take a list of "
          + "\n\tFILE(s) seperated by a space"
          + "\n\n\tSample Output : $ cat picturefile"
          + "\n\tThis is a picture file"
          + "\n\n\tSample Output : $ cat C/pics/picturefile text C/hello"
          + "\n\tThis is a picture file"
          + "\n\n\n\tHello World"
          + "\n\n\n\tHow are you QUIT");
      
      my_dict.put("echo", "Command: echo"
      		+ "\nParameters: String, and arrow or double arrow followed by filename"
      		+ "\nIf provided without arrow and fileaname, it simply prints out string in the console"
      		+ "\nIf provided with a single arrow and filename, it overwrites that file's content with the string"
      		+ "\nIf provided with double arrow and filename, it appends that file's content with the string ");
      
      my_dict.put("man", "Command: man"
      		+ "\nProvides documentation on all commands within THIS Java Shell"
      		+ "\nProvides information such as arguments and function.");
      
      my_dict.put("exit", "Command : exit"
          + "\n\n\tCloses the current session and leaves the Shell"
          + "\n\n\tParameter : None");     
  }

  
}
