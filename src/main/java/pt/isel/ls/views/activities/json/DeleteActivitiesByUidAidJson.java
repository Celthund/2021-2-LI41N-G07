package pt.isel.ls.views.activities.json;

import java.util.LinkedList;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.mappers.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.DeleteActivitiesByUidAidResult;
import pt.isel.ls.views.View;
import static pt.isel.ls.views.builders.json.JsonBuilder.*;
import static pt.isel.ls.views.builders.json.JsonGetter.emptyDataSetJson;
import static pt.isel.ls.views.builders.json.JsonGetter.getActivityJson;
import pt.isel.ls.views.builders.json.parts.JsonObject;

public class DeleteActivitiesByUidAidJson implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws AppException {
        LinkedList<Activity> activities = ((DeleteActivitiesByUidAidResult) requestResult).getData();
        LinkedList<JsonObject> objects = new LinkedList<>();

        if (activities == null) {
            return emptyDataSetJson(requestResult.getMessage(),
                requestResult.getStatus()).toString();
        }

        for (Activity activity : activities) {
            objects.add(getActivityJson(activity));
        }

        return jsonObject(
            jsonPut("DeletedActivities", jsonArray(objects))
        ).toString();
    }
}
