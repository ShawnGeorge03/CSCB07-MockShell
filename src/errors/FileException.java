package errors;

@SuppressWarnings("serial")
public class FileException extends InvalidArgsProvidedException {
    public FileException(String message){
        super(message);
    }
}