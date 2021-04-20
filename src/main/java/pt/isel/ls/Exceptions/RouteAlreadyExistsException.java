package pt.isel.ls.Exceptions;

public class RouteAlreadyExistsException extends Exception {
    public RouteAlreadyExistsException() {
        super();
    }
    public RouteAlreadyExistsException(String message) {
        super(message);
    }
}
