package pt.isel.ls.exceptions;

public class RouteNotFoundException extends AppException {
    public RouteNotFoundException() {
        super("Route not found.");
    }

    public RouteNotFoundException(String message) {
        super(message);
    }
}
