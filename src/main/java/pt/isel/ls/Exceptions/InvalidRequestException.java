package pt.isel.ls.Exceptions;

public class InvalidRequestException extends AppException{
    public InvalidRequestException() {
        super();
    }
    public InvalidRequestException(String message) {
        super(message);
    }
}
