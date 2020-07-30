package errors;

@SuppressWarnings("serial")
public class MalformedInputException extends Exception {

    public MalformedInputException(String message) {
        super(message);
    }
}