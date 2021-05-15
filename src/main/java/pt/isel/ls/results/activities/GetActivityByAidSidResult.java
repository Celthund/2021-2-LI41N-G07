package pt.isel.ls.results.activities;

import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;

public class GetActivityByAidSidResult extends RequestResult<Activity> {

    public GetActivityByAidSidResult(int status, Activity data, String message) {
        super(status, data, message);
    }
}
