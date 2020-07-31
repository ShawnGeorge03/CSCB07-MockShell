package errors;

@SuppressWarnings("serial")
public class InvalidRedirectionError extends InvalidArgsProvidedException {

    public InvalidRedirectionError(String message) {
        super(message);
    }
}