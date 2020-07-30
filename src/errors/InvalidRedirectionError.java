package errors;

@SuppressWarnings("serial")
public class InvalidRedirectionError extends Exception {

    public InvalidRedirectionError(String message) {
        super(message);
    }
}