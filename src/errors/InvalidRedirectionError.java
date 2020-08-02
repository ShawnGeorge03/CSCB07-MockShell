package errors;

/**
 * Class InvalidRedirectionError handles expections 
 * related to invalid use of redirection by user
 */


@SuppressWarnings("serial")
public class InvalidRedirectionError extends InvalidArgsProvidedException {

    /**
     * Handles the message to be sent to the console
     * 
     * @param message the message to be sent about error
     */
    public InvalidRedirectionError(String message) {
        //Returns an error message from where it was thrown
        super(message);
    }
}