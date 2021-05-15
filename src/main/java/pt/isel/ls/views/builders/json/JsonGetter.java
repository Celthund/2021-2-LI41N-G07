package pt.isel.ls.views.builders.json;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.views.builders.json.parts.JsonObject;

import static pt.isel.ls.views.builders.json.JsonBuilder.jsonObject;
import static pt.isel.ls.views.builders.json.JsonBuilder.jsonPut;

public class JsonGetter {
    public static JsonObject getUserJson(User user) throws InvalidJsonException {
        return jsonObject(
                jsonPut("User ID", user.id),
                jsonPut("Name", user.name),
                jsonPut("Email", user.email)
        );
    }
    public static JsonObject getSportJson(Sport sport) throws InvalidJsonException {
        return jsonObject(
                jsonPut("Sport ID", sport.sid),
                jsonPut("Name", sport.name),
                jsonPut("Description", sport.description)
        );
    }
    public static JsonObject getRouteJson(Route route) throws InvalidJsonException {
        return jsonObject(
                jsonPut("Sport ID", route.rid),
                jsonPut("StartLocation", route.startLocation),
                jsonPut("EndLocation", route.endLocation),
                jsonPut("Distance", route.distance)
        );
    }

    public static JsonObject getActivityJson(Activity activity) throws InvalidJsonException {
        JsonObject userJsonObject = getUserJson(activity.user);
        JsonObject sportJsonObject = getSportJson(activity.sport);
        JsonObject routeJsonObject;
        if (activity.route == null) {
            routeJsonObject = jsonObject(jsonPut("Route", null));
        } else {
            routeJsonObject = getRouteJson(activity.route);
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
