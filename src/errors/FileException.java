package errors;

@SuppressWarnings("serial")
public class FileException extends Exception {
    public FileException(String message){
        super(message);
    }
}