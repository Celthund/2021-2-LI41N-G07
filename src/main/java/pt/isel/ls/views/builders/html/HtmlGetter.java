package pt.isel.ls.views.builders.html;

import java.util.LinkedList;
import pt.isel.ls.mappers.domainclasses.Activity;
import static pt.isel.ls.mappers.domainclasses.Activity.durationToString;
import pt.isel.ls.mappers.domainclasses.Route;
import pt.isel.ls.mappers.domainclasses.Sport;
import pt.isel.ls.mappers.domainclasses.User;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;

public class HtmlGetter {

    // Static method that returns the code with the User Information ordered appropriated
    // The user information is received in the parameter
    public static LinkedList<Element> getActivityHtmlList(Activity activity) {
        LinkedList<Element> elements = new LinkedList<>();

        elements.addAll(getUserHtmlList(activity.user));
        elements.addAll(getSportHtmlList(activity.sport));

        if (activity.route != null) {
            elements.addAll(getRouteHtmlList(activity.route));
        }

        elements.add(dt("Date: " + activity.date));
        elements.add(dt("Duration: " + durationToString(activity.duration)));


        return elements;
    }

    // Static method that returns the code with the Sport Information ordered appropriated
    // The sport information is received in the parameter
    public static LinkedList<Element> getSportHtmlList(Sport sport) {
        LinkedList<Element> elements = new LinkedList<>();

        elements.add(dt("Sport id: " + sport.sid));
        elements.add(dd("Name: " + sport.name));
        elements.add(dd("Description: " + sport.description));

        return elements;
    }

    // Static method that returns the code with the Route Information ordered appropriated
    // The route information is received in the parameter
    public static LinkedList<Element> getRouteHtmlList(Route route) {
        LinkedList<Element> elements = new LinkedList<>();

        elements.add(dt("Route id: " + route.rid));
        elements.add(dd("Start Location: " + route.startLocation));
        elements.add(dd("End Location: " + route.endLocation));
        elements.add(dd("Distance: " + route.distance));

        return elements;
    }

    // Static method that returns the code with the Activity Information ordered appropriated
    // The activity information is received in the parameter
    public static LinkedList<Element> getUserHtmlList(User user) {
        LinkedList<Element> elements = new LinkedList<>();

        elements.add(dt("User id: " + user.id));
        elements.add(dd("Name: " + user.name));
        elements.add(dd("Email: " + user.email));

        return elements;
    }

    public static LinkedList<Element> getActivityHtmlTableHeader() {
        LinkedList<Element> elements = new LinkedList<>();

        elements.add(tr(
            th("Activity Id"),
            th("Date"),
            th("Duration"),
            th("Name"),
            th("Start Location"),
            th("End Location"),
            th("Distance")
        ));

        return elements;
    }

    public static LinkedList<Element> getSportHtmlTableHeader() {
        LinkedList<Element> elements = new LinkedList<>();

        elements.add(tr(
            th("Sport Id"),
            th("Name"),
            th("Description")
        ));

        return elements;
    }

    public static LinkedList<Element> getActivityHtmlTableRow(Activity activity) {
        LinkedList<Element> elements = new LinkedList<>();

        // User id, Name, Email, Sport id, Name, Description, Route id
        elements.add(
            td(alink("/sports/" + activity.sport.sid + "/activities/" + activity.aid, Integer.toString(activity.aid))));
        elements.add(td(activity.date.toString()));
        elements.add(td(durationToString(activity.duration)));
        elements.add(td(alink("/users/" + activity.user.id, activity.user.name)));

        if (activity.route != null) {
            elements.addAll(getRouteHtmlTableRow(activity.route));
        }

        return elements;
    }


    public static LinkedList<Element> getSportHtmlTableRow(Sport sport) {
        LinkedList<Element> elements = new LinkedList<>();

        // Sport id, Name, Description
        elements.add(td(Integer.toString(sport.sid)));
        elements.add(td(sport.name));
        elements.add(td(sport.description));

        return elements;
    }

    public static LinkedList<Element> getRouteHtmlTableRow(Route route) {
        LinkedList<Element> elements = new LinkedList<>();

        // Start Location, End Location, Distance
        elements.add(td(route.startLocation));
        elements.add(td(route.endLocation));
        elements.add(td(Integer.toString(route.distance)));


        return elements;
    }

    public static LinkedList<Element> getUserHtmlTableRow(User user) {
        LinkedList<Element> elements = new LinkedList<>();

        // User id, Name, Email
        elements.add(td(Integer.toString(user.id)));
        elements.add(td(user.name));
        elements.add(td(user.email));

        return elements;
    }

    public static Element emptyDataSetHtml(String message) {
        return html(
            head(
                title(message)
            ),
            body(
                alink("/", "Homepage"),
                br(),
                h1(message)
            )
        );
    }
}