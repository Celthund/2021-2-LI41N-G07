package pt.isel.ls.views.builders.html;

import java.util.LinkedList;
import pt.isel.ls.models.domainclasses.Activity;
import static pt.isel.ls.models.domainclasses.Activity.durationToString;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.models.domainclasses.User;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;

public class HtmlGetter {

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

    public static LinkedList<Element> getSportHtmlList(Sport sport) {
        LinkedList<Element> elements = new LinkedList<>();

        elements.add(dt("Sport id: " + sport.sid));
        elements.add(dd("Name: " + sport.name));
        elements.add(dd("Description: " + sport.description));

        return elements;
    }

    public static LinkedList<Element> getRouteHtmlList(Route route) {
        LinkedList<Element> elements = new LinkedList<>();

        elements.add(dt("Route id: " + route.rid));
        elements.add(dd("Start Location: " + route.startLocation));
        elements.add(dd("End Location: " + route.endLocation));
        elements.add(dd("Distance: " + route.distance));

        return elements;
    }

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
            th("User Id"),
            th("User Name"),
            th("User Email"),
            th("Sport Id"),
            th("Sport Name"),
            th("Sport Description"),
            th("Router Id"),
            th("Start Location"),
            th("End Location"),
            th("Distance")
        ));

        return elements;
    }

    public static LinkedList<Element> getActivityHtmlTableRow(Activity activity) {
        LinkedList<Element> elements = new LinkedList<>();

        // User id, Name, Email, Sport id, Name, Description, Route id
        // , Start Location, End Location, Distance
        // , Date Duration
        elements.add(td(Integer.toString(activity.aid)));
        elements.add(td(activity.date.toString()));
        elements.add(td(durationToString(activity.duration)));

        elements.addAll(getUserHtmlTableRow(activity.user));
        elements.addAll(getSportHtmlTableRow(activity.sport));

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

        // Route id, Start Location, End Location, Distance
        elements.add(td(Integer.toString(route.rid)));
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
                h1(message)
            )
        );
    }
}