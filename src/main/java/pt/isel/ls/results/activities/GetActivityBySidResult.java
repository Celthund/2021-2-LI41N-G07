package pt.isel.ls.results.activities;

import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;

public class GetActivityBySidResult implements RequestResult {
    public final int status;
    public final Activity data;
    public final String message;

    public GetActivityBySidResult(int status, Activity data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
