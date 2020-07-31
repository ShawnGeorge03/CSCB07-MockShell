package errors;

@SuppressWarnings("serial")
public class MissingQuotesException extends InvalidArgsProvidedException {

    public MissingQuotesException(String message) {
        super(message);
    }
}