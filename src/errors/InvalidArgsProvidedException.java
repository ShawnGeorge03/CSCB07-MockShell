package errors;

@SuppressWarnings("serial")
public class InvalidArgsProvidedException extends Exception {

    public InvalidArgsProvidedException(String message) {
        super(message);
    }
}