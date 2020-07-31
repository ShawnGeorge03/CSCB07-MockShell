package errors;

@SuppressWarnings("serial")
public class DirectoryException extends InvalidArgsProvidedException {
    public DirectoryException(String message){
        super(message);
    }
}