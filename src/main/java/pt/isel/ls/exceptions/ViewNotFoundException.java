package pt.isel.ls.exceptions;

public class ViewNotFoundException extends AppException {
    public ViewNotFoundException() {
        super("View not found.");
    }

    public ViewNotFoundException(String message) {
        super(message);
    }
}
