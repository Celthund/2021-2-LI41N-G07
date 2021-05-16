package pt.isel.ls.results.activities;

import java.util.LinkedList;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;

public class GetActivitiesByTopsResult extends RequestResult<LinkedList<Activity>> {

    public GetActivitiesByTopsResult(int status, LinkedList<Activity> data, String message) {
        super(status, data, message);
    }
}
