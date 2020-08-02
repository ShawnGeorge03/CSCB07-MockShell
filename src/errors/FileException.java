package errors;

/**
 * Class FileException handles expections 
 * related to invalid file operations
 */

@SuppressWarnings("serial")
public class FileException extends InvalidArgsProvidedException {

    /**
     * Handles the message to be sent to the console
     * 
     * @param message the message to be sent about error
     */
    public FileException(String message){
        //Returns an error message from where it was thrown
        super(message);
    }
}