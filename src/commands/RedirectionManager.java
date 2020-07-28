package commands;

import java.util.ArrayList;
import java.util.Arrays;

import data.FileSystemI;

public class RedirectionManager {

    /**
     * Declare instance of ErrorHandler to handle error messages
     */
    private ErrorHandler errorManager; 

    /**
     * Declares instance variable of ArrayList that holds all the commands that can
     * handle redirection
     */
    private ArrayList<String> redirectClasses;

    String mode, fileName;

    public RedirectionManager() {
        errorManager = new ErrorHandler();
        // Creates a ArrayList Object called redirectClasses
        redirectClasses = new ArrayList<String>();
        // Initializes the ArrayList with values
        redirectClasses.addAll(Arrays.asList("cat", "echo", "find", "history", "ls", "man", "pwd", "tree"));
    }

    public boolean isRedirectionableCommand(FileSystemI fs, String fullInput) {
        String command = fullInput.split(" ")[0];

        // If the given command is a redirectionalble command
        if (redirectClasses.contains(command)) {
            return true;
        } else {
            // Checks if the command uses either > or >> in the parameters and returns an
            // error if so
            if (Arrays.asList(fullInput.split(" ")).contains(">")
                    || Arrays.asList(fullInput.split(" ")).contains(">>")) {
                // Returns the respective error
                outputResult(fs,errorManager.getError("Redirection Not allowed", command + " does not support redirection"));
                return false;
            }
        }
        return redirectClasses.contains(fullInput.split(" ")[0]);
    }

    public String[] setParams(FileSystemI fs, String fullInput) {
        // Initializes an array containing the words of the parsedInput
        String[] params = fullInput.split(" ");

        // If the user used the single arrow > which sets redirection to overwrite a
        // file
        if (Arrays.asList(params).contains(">")) {
            // Sets the boolean value to true
            mode = "O";
            // Collects the file name
            fileName = setFileName(params, ">");
            // Collects the parameters for the command
            params = Arrays.copyOfRange(params, 1, Arrays.asList(params).indexOf(">"));
            // If the user used the single arrow >> which sets redirection to append a file
        } else if (Arrays.asList(params).contains(">>")) {
            // Sets the boolean value to true
            mode = "A";
            // Collects the file name
            fileName = setFileName(params, ">>");
            // Collects the parameters for the command
            params = Arrays.copyOfRange(params, 1, Arrays.asList(params).indexOf(">>"));
        }else{
            params = Arrays.copyOfRange(fullInput.split(" "), 1, fullInput.split(" ").length);
            fileName = "";
            mode = "";
        }

        if(fileName.startsWith("Error")){
            outputResult(fs, fileName);
            return null;
        } 

        // Returns the parameters
        return params;
    }

    private String setFileName(String[] params, String type){
        String[] fileName = Arrays.copyOfRange(params, Arrays.asList(params).indexOf(type) + 1, params.length);
        if(fileName.length == 0){
            return errorManager.getError("No parameters provided", "");
        }else if(fileName.length > 1){
            String parameter = Arrays.toString(params);
            parameter = parameter.substring(1, parameter.length() - 1).replace(",", "").trim();
            return errorManager.getError("Multiple parameters provided", parameter + " Only one is required");
        }
        return fileName[0];
    }

    /**
     * Manages the output that results from a user input
     * 
     * @param result the string to be outputed to the console
     */

    public String outputResult(FileSystemI fs, String result) {
        // Checks if the operation returns any input or not
        if (result == null) {
            return null;
            // If the return was some sort of an Error prints the error out
        } else if (result.startsWith("Error")) {
            return result.trim();
            // If the user want to overwrite a file with this new result
        } else if (mode.equals("O")) {
            fs.fileOverwrite(result, fileName);
            // Resets the redirection
            mode = "";
            return null;
            // If the user want to append to the file with this new result
        } else if (mode.equals("A")) {
            fs.fileAppend(result, fileName);
            // Resets the redirection
            mode = "";
            return null;
            // If the user chooses not to redirect the results to a file
        } else {
            return result.trim();
        }
    }
}