package pt.isel.ls.Exceptions;

public class InvalidDurationException extends AppException {
    public InvalidDurationException() {
        super();
    }
    public InvalidDurationException(String message) {
        super(message);
    }
}