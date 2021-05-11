package pt.isel.ls.results.activities;

import java.util.LinkedList;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;

public class GetActivitiesByTopsResult implements RequestResult {
    public final int status;
    public final LinkedList<Activity> data;
    public final String message;

    public GetActivitiesByTopsResult(int status, LinkedList<Activity> data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
