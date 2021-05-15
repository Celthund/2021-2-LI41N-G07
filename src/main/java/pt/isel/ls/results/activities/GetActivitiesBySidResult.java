package pt.isel.ls.results.activities;

import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import java.util.LinkedList;

public class GetActivitiesBySidResult extends RequestResult<LinkedList<Activity>> {

    public GetActivitiesBySidResult(int status, LinkedList<Activity> data, String message) {
        super(status, data, message);
    }
}
