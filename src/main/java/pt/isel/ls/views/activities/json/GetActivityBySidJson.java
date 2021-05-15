package pt.isel.ls.views.activities.json;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivityBySidResult;
import pt.isel.ls.views.View;

import static pt.isel.ls.views.builders.json.JsonGetter.*;

public class GetActivityBySidJson implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) throws InvalidJsonException {
        Activity activity = ((GetActivityBySidResult) requestResult).data;

        return getActivityJson(activity).toString();
    }
}
