package pt.isel.ls.results;

public class RootResult extends RequestResult<Object> {
    public RootResult(int status, Object data, String message) {
        super(status, data, message);
    }
}
