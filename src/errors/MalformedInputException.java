package errors;

@SuppressWarnings("serial")
public class MalformedInputException extends InvalidArgsProvidedException {

    public MalformedInputException(String message) {
        super(message);
    }
}