package pt.isel.ls.views.builders.plaintext;

import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.models.domainclasses.User;

public class PlainTextGetter {

    public static String getUserPlainText(User user) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("User {uid: ")
            .append(user.id)
            .append(", name: ")
            .append(user.name)
            .append(", email: ")
            .append(user.email).append("}");

        return stringBuilder.toString();
    }

    public static String getSportPlainText(Sport sport) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Sport {sid: ")
            .append(sport.sid)
            .append(", name: ")
            .append(sport.name)
            .append(", description: ")
            .append(sport.description)
            .append("}");

        return stringBuilder.toString();
    }

    public static String getRoutePlainText(Route route) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Route {rid: ")
            .append(route.rid)
            .append(", distance: ")
            .append(route.distance)
            .append(", startLocation: ")
            .append(route.startLocation)
            .append(", endLocation: ")
            .append(route.endLocation)
            .append("}");
        return stringBuilder.toString();
    }

    public static String getActivityPlainText(Activity activity) {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("Activity {aid: ")
            .append(activity.aid)
            .append(", date: ")
            .append(activity.date)
            .append(", duration: ")
            .append(activity.duration)
            .append(", ")
            .append(getUserPlainText(activity.user))
            .append(", ")
            .append(getSportPlainText(activity.sport));

        if (activity.route != null) {
            stringbuilder.append(", ")
                .append(getRoutePlainText(activity.route));
        }
        stringbuilder.append("}");

        return stringbuilder.toString();
    }
}
