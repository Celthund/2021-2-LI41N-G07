package pt.isel.ls.views.builders.json;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Activity;
import static pt.isel.ls.models.domainclasses.Activity.durationToString;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.models.domainclasses.User;
import static pt.isel.ls.views.builders.json.JsonBuilder.jsonObject;
import static pt.isel.ls.views.builders.json.JsonBuilder.jsonPut;
import pt.isel.ls.views.builders.json.parts.JsonObject;

public class JsonGetter {
    // Static method that returns the code with the User Information ordered appropriated
    // The user information is received in the parameter
    public static JsonObject getUserJson(User user) throws InvalidJsonException {
        return jsonObject(
            jsonPut("User ID", user.id),
            jsonPut("Name", user.name),
            jsonPut("Email", user.email)
        );
    }

    // Static method that returns the code with the Sport Information ordered appropriated
    // The sport information is received in the parameter
    public static JsonObject getSportJson(Sport sport) throws InvalidJsonException {
        return jsonObject(
            jsonPut("Sport ID", sport.sid),
            jsonPut("Name", sport.name),
            jsonPut("Description", sport.description)
        );
    }

    // Static method that returns the code with the Route Information ordered appropriated
    // The route information is received in the parameter
    public static JsonObject getRouteJson(Route route) throws InvalidJsonException {
        return jsonObject(
            jsonPut("Route ID", route.rid),
            jsonPut("StartLocation", route.startLocation),
            jsonPut("EndLocation", route.endLocation),
            jsonPut("Distance", route.distance)
        );
    }

    // Static method that returns the code with the Activity Information ordered appropriated
    // The activity information is received in the parameter
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
            jsonPut("Activity ID", activity.aid),
            jsonPut("User", userJsonObject),
            jsonPut("Sports", sportJsonObject),
            jsonPut("Route", routeJsonObject),
            jsonPut("Date", activity.date),
            jsonPut("Duration", durationToString(activity.duration))
        );
    }

    // In case there was no data gotten it will default to this method
    public static JsonObject emptyDataSetJson(String message, int status) throws InvalidJsonException {
        return jsonObject(
            jsonPut("Message", message),
            jsonPut("Status", status)
        );
    }
}
