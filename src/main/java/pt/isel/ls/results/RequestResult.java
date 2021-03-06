package pt.isel.ls.results;

import java.util.HashMap;
import pt.isel.ls.request.Request;

public abstract class RequestResult<T> {
    // Stores the result status
    private final int status;
    // Data is generic because it will be alink Table holder value (User, Sports, etc...)
    private final T data;
    // Stores the message to be sent to the user (if needed)
    private final String message;
    private final HashMap<String, String> headers;
    private Request request;

    protected RequestResult(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
        headers = new HashMap<>();
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

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }
}
