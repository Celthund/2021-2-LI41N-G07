package pt.isel.ls.Exceptions;

// Server Error Exception to replace the SqlServer exception, for a more user friendly exception with a custom message
public class ServerErrorException extends AppException {
    public ServerErrorException() {
        super();
    }
    public ServerErrorException(String message) {
        super(message);
    }
}
