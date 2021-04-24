package pt.isel.ls.Exceptions;

public class RouteAlreadyExistsException extends AppException {
    public RouteAlreadyExistsException() {
        super();
    }
    public RouteAlreadyExistsException(String message) {
        super(message);
    }
}
