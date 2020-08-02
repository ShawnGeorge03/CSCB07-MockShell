package errors;

/**
 * Class MissingQuotesException handles expections
 * related to missing quotaations in text
 */

@SuppressWarnings("serial")
public class MissingQuotesException extends InvalidArgsProvidedException {

    /**
     * Handles the message to be sent to the console
     * 
     * @param message the message to be sent about error
     */
    public MissingQuotesException(String message) {
        //Returns an error message from where it was thrown
        super(message);
    }
}