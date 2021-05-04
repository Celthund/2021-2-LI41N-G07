package pt.isel.ls.exceptions;

public class InvalidRequestException extends AppException {
    public InvalidRequestException() {
        super("Invalid Request.");
    }

    public InvalidRequestException(String message) {
        super(message);
    }
}
