package errors;

/**
 * Class MalformedInputException handles expections 
 * related to malformed text by improper use of quotations
 */

@SuppressWarnings("serial")
public class MalformedInputException extends InvalidArgsProvidedException {

    /**
     * Handles the message to be sent to the console
     * 
     * @param message the message to be sent about error
     */
    public MalformedInputException(String message) {
        //Returns an error message from where it was thrown
        super(message);
    }
}