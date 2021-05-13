package pt.isel.ls.views.users.json.activities;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.CreateActivityResult;

public class CreateActivityJson extends AbstractActivityJson {
    @Override
    public String getRepresentation(RequestResult requestResult) throws InvalidJsonException {
        Activity activity = ((CreateActivityResult) requestResult).data;
        return getActivityJson(activity).toString();
    }
}
