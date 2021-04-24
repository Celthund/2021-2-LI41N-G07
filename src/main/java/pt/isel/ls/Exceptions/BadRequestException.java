package pt.isel.ls.Exceptions;

public class BadRequestException extends AppException {
    public BadRequestException() {
        super();
    }
    public BadRequestException(String message) {
        super(message);
    }
}
