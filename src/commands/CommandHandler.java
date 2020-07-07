package commands;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;

public class CommandHandler {

	String[] splitInput;
	String command;
	HashMap<String, String> commandLibrary = new HashMap<String, String>();
	
	boolean speakMode = false;
	
	public CommandHandler() {
		fillLibrary();
	}
	
	public void fillLibrary() {
		commandLibrary.put("mkdir", "Mkdir");
		commandLibrary.put("cat", "Cat");
	}
	
	public void setCommand(String parsedInput) {
	    splitInput = parsedInput.split(" ");
	    command = splitInput[0];
	    String args[] = Arrays.copyOfRange(splitInput, 1, splitInput.length);
	    
	    if(speakMode) {
	      this.command = "speak";
	      args = splitInput;
	    }
	    
	    run(command, args, parsedInput);
	}
	
	public void run(String command, String[] arguments, String fullInput) {  
		try {
			Class currentCommand = Class.forName(commandLibrary.get(command));
			Constructor constructor = currentCommand.getConstructor(String[].class);
			
			Object newCommand = constructor.newInstance(arguments);
			((Command) newCommand.run());
		} catch (ClassNotFoundException e) {
			//ERROR
		}
		
		
	}
}
