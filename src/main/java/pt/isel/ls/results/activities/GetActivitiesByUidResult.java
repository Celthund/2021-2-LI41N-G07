package pt.isel.ls.results.activities;

import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import java.util.LinkedList;

public class GetActivitiesByUidResult extends RequestResult<LinkedList<Activity>> {
    public GetActivitiesByUidResult(int status, LinkedList<Activity> data, String message) {
        super(status, data, message);
    }
}
