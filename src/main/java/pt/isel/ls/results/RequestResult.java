package pt.isel.ls.results;

public abstract class RequestResult <T> {
    private final int status;
    private final T data;
    private final String message;

    protected RequestResult(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public T getData(){
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
