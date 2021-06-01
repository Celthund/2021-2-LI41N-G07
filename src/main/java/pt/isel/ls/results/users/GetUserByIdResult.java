package pt.isel.ls.results.users;

import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;

import java.util.LinkedList;

public class GetUserByIdResult extends RequestResult<LinkedList<Activity>> {

    public GetUserByIdResult(int status, LinkedList<Activity> data, String message) {
        super(status, data, message);
    }
}
