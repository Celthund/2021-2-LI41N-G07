package pt.isel.ls.Exceptions;

public class RouteNotFoundException extends Exception {
    public RouteNotFoundException() {
        super();
    }
    public RouteNotFoundException(String message) {
        super(message);
    }
}
