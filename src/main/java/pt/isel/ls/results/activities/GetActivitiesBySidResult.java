package pt.isel.ls.results.activities;

import java.util.LinkedList;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;

public class GetActivitiesBySidResult extends RequestResult<LinkedList<Activity>> {

    public GetActivitiesBySidResult(int status, LinkedList<Activity> data, String message) {
        super(status, data, message);
    }
}
