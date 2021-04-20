package pt.isel.ls.Exceptions;

public class InvalidRequestException extends Exception{
    public InvalidRequestException() {
        super();
    }
    public InvalidRequestException(String message) {
        super(message);
    }
}
