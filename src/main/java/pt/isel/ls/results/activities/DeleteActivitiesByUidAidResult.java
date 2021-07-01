package pt.isel.ls.results.activities;

import java.util.LinkedList;
import pt.isel.ls.mappers.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;

public class DeleteActivitiesByUidAidResult extends RequestResult<LinkedList<Activity>> {

    public DeleteActivitiesByUidAidResult(int status, LinkedList<Activity> data, String message) {
        super(status, data, message);
    }
}
