package pt.isel.ls.views.users.json.activities;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.json.parts.JsonObject;

import static pt.isel.ls.views.builders.json.JsonBuilder.jsonObject;
import static pt.isel.ls.views.builders.json.JsonBuilder.jsonPut;

public abstract class AbstractActivityJson implements View {

    protected JsonObject getActivityJson(Activity activity) throws InvalidJsonException {
        JsonObject userJsonObject = jsonObject(
                jsonPut("User ID", activity.user.id),
                jsonPut("Name", activity.user.name),
                jsonPut("Email", activity.user.email)
        );
        JsonObject sportJsonObject = jsonObject(
                jsonPut("Sport ID", activity.sport.sid),
                jsonPut("Name", activity.sport.name),
                jsonPut("Description", activity.sport.description)
        );
        JsonObject routeJsonObject;
        if (activity.route == null) {
            routeJsonObject = jsonObject(jsonPut("Route", null));
        } else {
            routeJsonObject = jsonObject(
                    jsonPut("Sport ID", activity.route.rid),
                    jsonPut("StartLocation", activity.route.startLocation),
                    jsonPut("EndLocation", activity.route.endLocation),
                    jsonPut("Distance", activity.route.distance)
            );
        }

        return jsonObject(
                //aid, uid, rid, sid, date, duration
                jsonPut("Activity ID", activity.id),
                jsonPut("User", userJsonObject),
                jsonPut("Sports", sportJsonObject),
                jsonPut("Route", routeJsonObject),
                jsonPut("Date", activity.date),
                jsonPut("Duration", activity.duration)
        );
    }
}
