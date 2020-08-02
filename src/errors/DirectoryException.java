package errors;

/**
 * Class DirectoryException handles expections 
 * related to invalid directory operations
 */

@SuppressWarnings("serial")
public class DirectoryException extends InvalidArgsProvidedException {

    /**
     * Handles the message to be sent to the console
     * 
     * @param message the message to be sent about error
     */
    public DirectoryException(String message){
        //Returns an error message from where it was thrown
        super(message);
    }
}