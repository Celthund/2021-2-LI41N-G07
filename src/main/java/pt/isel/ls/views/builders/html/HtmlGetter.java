package pt.isel.ls.views.builders.html;

import pt.isel.ls.models.domainclasses.*;

import java.util.LinkedList;

import static pt.isel.ls.models.domainclasses.Activity.durationToString;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;

public class HtmlGetter {

    public static LinkedList<Element> getActivityHTMLList(Activity activity) {
        LinkedList<Element> elements = new LinkedList<>();

        elements.addAll(getUserHTMLList(activity.user));
        elements.addAll(getSportHTMLList(activity.sport));

        if (activity.route != null) {
            elements.addAll(getRouteHTMLList(activity.route));
        }

        elements.add(dt("Date: " + activity.date));
        elements.add(dt("Duration: " + durationToString(activity.duration)));


        return elements;
    }


    public static LinkedList<Element> getSportHTMLList(Sport sport) {
        LinkedList<Element> elements = new LinkedList<>();

        elements.add(dt("Sport id: " + sport.sid));
        elements.add(dd("Name: " + sport.name));
        elements.add(dd("Description: " + sport.description));

        return elements;
    }

    public static LinkedList<Element> getRouteHTMLList(Route route) {
        LinkedList<Element> elements = new LinkedList<>();

        elements.add(dt("Route id: " + route.rid));
        elements.add(dd("Start Location: " + route.startLocation));
        elements.add(dd("End Location: " + route.endLocation));
        elements.add(dd("Distance: " + route.distance));

        return elements;
    }

    public static LinkedList<Element> getUserHTMLList(User user) {
        LinkedList<Element> elements = new LinkedList<>();

        elements.add(dt("User id: " + user.id));
        elements.add(dd("Name: " + user.name));
        elements.add(dd("Email: " + user.email));

        return elements;
    }

    public static LinkedList<Element> getActivityHTMLTableRow(Activity activity) {
        LinkedList<Element> elements = new LinkedList<>();

        // User id, Name, Email, Sport id, Name, Description, Route id
        // , Start Location, End Location, Distance
        // , Date Duration
        elements.add(td(Integer.toString(activity.aid)));
        elements.add(td(activity.date.toString()));
        elements.add(td(durationToString(activity.duration)));

        elements.addAll(getUserHTMLTableRow(activity.user));
        elements.addAll(getSportHTMLTableRow(activity.sport));

        if (activity.route != null) {
            elements.addAll(getRouteHTMLTableRow(activity.route));
        }



        return elements;
    }


    public static LinkedList<Element> getSportHTMLTableRow(Sport sport) {
        LinkedList<Element> elements = new LinkedList<>();

        // Sport id, Name, Description
        elements.add(td(Integer.toString(sport.sid)));
        elements.add(td( sport.name));
        elements.add(td(sport.description));

        return elements;
    }

    public static LinkedList<Element> getRouteHTMLTableRow(Route route) {
        LinkedList<Element> elements = new LinkedList<>();

        // Route id, Start Location, End Location, Distance
        elements.add(td(Integer.toString(route.rid)));
        elements.add(td(route.startLocation));
        elements.add(td(route.endLocation));
        elements.add(td(Integer.toString(route.distance)));


        return elements;
    }

    public static LinkedList<Element> getUserHTMLTableRow(User user) {
        LinkedList<Element> elements = new LinkedList<>();

        // User id, Name, Email
        elements.add(td(Integer.toString(user.id)));
        elements.add(td(user.name));
        elements.add(td(user.email));

        return elements;
    }
}