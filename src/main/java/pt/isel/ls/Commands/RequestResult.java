package pt.isel.ls.Commands;

public class RequestResult {

    public final int status;
    public final Object data;
    public final String message;

    public RequestResult(int status, Object data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
