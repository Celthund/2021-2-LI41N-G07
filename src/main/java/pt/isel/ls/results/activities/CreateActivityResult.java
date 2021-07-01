package pt.isel.ls.results.activities;

import pt.isel.ls.mappers.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;

public class CreateActivityResult extends RequestResult<Activity> {


    public CreateActivityResult(int status, Activity data, String message) {
        super(status, data, message);
    }
}
