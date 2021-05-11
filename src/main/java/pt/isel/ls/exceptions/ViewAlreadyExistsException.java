package pt.isel.ls.exceptions;

public class ViewAlreadyExistsException extends AppException {
    public ViewAlreadyExistsException() {
        super("View already exists.");
    }

    public ViewAlreadyExistsException(String message) {
        super(message);
    }
}
