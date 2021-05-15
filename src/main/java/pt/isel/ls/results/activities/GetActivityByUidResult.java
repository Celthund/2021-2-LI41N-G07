package pt.isel.ls.results.activities;

import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;

import java.util.LinkedList;

public class GetActivityByUidResult implements RequestResult {
    public final int status;
    public final LinkedList<Activity> data;
    public final String message;

    public GetActivityByUidResult(int status, LinkedList<Activity> data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
