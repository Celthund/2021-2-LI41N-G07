package pt.isel.ls.results;

public class OptionResult extends RequestResult<Object> {
    public OptionResult(int status, Object data, String message) {
        super(status, data, message);
    }
}
