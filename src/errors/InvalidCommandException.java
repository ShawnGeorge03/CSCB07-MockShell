package errors;

/**
 * Class InvalidCommandException handles expections 
 * related to invalid command operations
 */

@SuppressWarnings("serial")
public class InvalidCommandException extends Exception {
    
    /**
     * Handles the message to be sent to the console
     * 
     * @param message the message to be sent about error
     */
    public InvalidCommandException(String message) {
        //Returns an error message from where it was thrown
        super(message);
    }
}