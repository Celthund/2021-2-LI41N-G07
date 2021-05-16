package pt.isel.ls.views.activities.json;

import java.util.LinkedList;
import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivitiesByUidResult;
import pt.isel.ls.views.View;
import static pt.isel.ls.views.builders.json.JsonBuilder.*;
import static pt.isel.ls.views.builders.json.JsonGetter.emptyDataSetJson;
import static pt.isel.ls.views.builders.json.JsonGetter.getActivityJson;
import pt.isel.ls.views.builders.json.parts.JsonObject;

public class GetActivitiesByUidJson implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws InvalidJsonException {
        LinkedList<Activity> activities = ((GetActivitiesByUidResult) requestResult).getData();
        LinkedList<JsonObject> objects = new LinkedList<>();

        if (activities == null) {
            return emptyDataSetJson(requestResult.getMessage(),
                requestResult.getStatus()).toString();
        }

        for (Activity activity : activities) {
            objects.add(getActivityJson(activity));
        }
        return jsonObject(jsonPut("Activities", jsonArray(objects))).toString();
    }
}
