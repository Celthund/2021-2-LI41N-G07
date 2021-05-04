package pt.isel.ls.exceptions;

public class BadRequestException extends AppException {
    public BadRequestException() {
        super("Bad request.");
    }

    public BadRequestException(String message) {
        super(message);
    }
}
