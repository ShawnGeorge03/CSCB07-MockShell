package errors;

/**
 * Class InvalidArgsProvidedException handles 
 * expections related to invalid user input 
 */

@SuppressWarnings("serial")
public class InvalidArgsProvidedException extends Exception {

    /**
     * Handles the message to be sent to the console
     * 
     * @param message the message to be sent about error
     */
    public InvalidArgsProvidedException(String message) {
        //Returns an error message from where it was thrown
        super(message);
    }
}