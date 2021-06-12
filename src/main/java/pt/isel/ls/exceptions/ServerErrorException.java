package pt.isel.ls.exceptions;

// Server Error Exception to replace the SqlServer exception,
// for alink more user friendly exception with alink custom message
public class ServerErrorException extends AppException {
    public ServerErrorException() {
        super("Server error.");
    }

    public ServerErrorException(String message) {
        super(message);
    }
}