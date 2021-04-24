package pt.isel.ls.Exceptions;

public class RouteNotFoundException extends AppException {
    public RouteNotFoundException() {
        super();
    }
    public RouteNotFoundException(String message) {
        super(message);
    }
}
