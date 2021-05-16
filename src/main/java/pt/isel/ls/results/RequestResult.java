package pt.isel.ls.results;

public abstract class RequestResult<T> {
    // Stores the result status
    private final int status;
    // Data is generic because it will be a Table holder value (User, Sports, etc...)
    private final T data;
    // Stores the message to be sent to the user (if needed)
    private final String message;

    protected RequestResult(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
