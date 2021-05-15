package pt.isel.ls.views.activities.json;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivityByUidResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.json.parts.JsonObject;

import java.util.LinkedList;

import static pt.isel.ls.views.builders.json.JsonBuilder.*;
import static pt.isel.ls.views.builders.json.JsonGetter.*;

public class GetActivityByUidJson implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) throws InvalidJsonException {
        LinkedList<Activity> activities = ((GetActivityByUidResult) requestResult).data;
        LinkedList<JsonObject> objects = new LinkedList<>();

        for (Activity activity : activities) {
            objects.add(getActivityJson(activity));
        }
        return jsonObject(jsonPut("Activities", jsonArray(objects))).toString();
    }
}
