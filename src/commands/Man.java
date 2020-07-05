package commands;

import java.util.Hashtable;
import commands.Command;

public class Man extends Command{
	Hashtable<String, String> my_dict;
	
	public Man() {
	  my_dict = new Hashtable<String, String>();
      setDictionary();
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
      my_dict.put("mkdir", "2");
      my_dict.put("cd", "3");
      my_dict.put("ls", "4");
      my_dict.put("pwd", "5");
      my_dict.put("pushd", "6");
      my_dict.put("popd", "7");
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
      my_dict.put("echo", "10");
      my_dict.put("man", "11");
      my_dict.put("exit", "Command : exit"
          + "\n\n\tCloses the current session and leaves the Shell"
          + "\n\n\tParameter : None");     
  }
}
