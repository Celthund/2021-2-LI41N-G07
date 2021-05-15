package pt.isel.ls.views.users.json.activities;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.CreateActivityResult;
import pt.isel.ls.views.View;

import static pt.isel.ls.views.users.json.JsonGetter.*;

public class CreateActivityJson implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) throws InvalidJsonException {
        Activity activity = ((CreateActivityResult) requestResult).data;
        return getActivityJson(activity).toString();
    }
}
