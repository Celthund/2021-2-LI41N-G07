package pt.isel.ls.exceptions;

public class InvalidJsonException extends AppException {
    public InvalidJsonException() {
        super("Key already exists.");
    }

    public InvalidJsonException(String message) {
        super(message);
    }
}
