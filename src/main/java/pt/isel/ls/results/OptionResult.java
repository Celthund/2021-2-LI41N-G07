package pt.isel.ls.results;

public class OptionResult implements RequestResult {
    public final int status;
    public final Object data;
    public final String message;

    public OptionResult(int status, Object data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
