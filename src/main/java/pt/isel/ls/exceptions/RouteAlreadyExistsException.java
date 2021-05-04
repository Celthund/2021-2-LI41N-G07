package pt.isel.ls.exceptions;

public class RouteAlreadyExistsException extends AppException {
    public RouteAlreadyExistsException() {
        super("Route already exists.");
    }

    public RouteAlreadyExistsException(String message) {
        super(message);
    }
}
