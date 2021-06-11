package pt.isel.ls.views.users.html;

import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetUserByIdResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;

import java.util.HashSet;
import java.util.LinkedList;
import static pt.isel.ls.models.domainclasses.Activity.durationToString;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;
import static pt.isel.ls.views.builders.html.HtmlGetter.*;

public class GetUserByIdHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) {
        LinkedList<Activity> user = ((GetUserByIdResult) requestResult).getData();

        if (user == null) {
            return emptyDataSetHtml(requestResult.getMessage()).toString();
        }


        LinkedList<Element> sportsElements = new LinkedList<>();
        sportsElements.add(tr(
                th("Sid"),
                th("Name"),
                th("Description")
        ));

        // Add HashSet to show only each Sport once
        HashSet<Integer> sportsList = new HashSet<>();

        for (Activity activity : user) {
            if (!sportsList.contains(activity.sport.sid)) {
                sportsList.add(activity.sport.sid);
                sportsElements.add(tr(
                        td(a("/sports/" + activity.sport.sid
                                + "?skip=0&top=5", Integer.toString(activity.sport.sid))),
                        td(activity.sport.name),
                        td(activity.sport.description)
                ));
            }
        }

        LinkedList<Element> activitiesElements = new LinkedList<>();
        LinkedList<Element> routesElements;

        activitiesElements.add(tr(
                th("Activities"),
                th("Date"),
                th("Duration"),
                th("Route Id"),
                th("Start Location"),
                th("End Location"),
                th("Distance")
        ));

        for (Activity activity : user) {
            routesElements = new LinkedList<>();
            routesElements.add(td(a("/sports/" + activity.sport.sid
                    + "/activities/" + activity.aid
                    + "?skip=0&top=5", Integer.toString(activity.aid))));
            routesElements.add(td(activity.date.toString()));
            routesElements.add(td(durationToString(activity.duration)));

            if (activity.route != null) {
                routesElements.addAll(getRouteHtmlTableRow(activity.route));
            }
            activitiesElements.add(tr(
                    routesElements.toArray((new Element[0]))
            ));
        }

        return html(
            head(
                title("User" + user.getFirst().user.id)
            ),
            body(
                h1("User ID: " + user.getFirst().user.id),
                ul(
                    li("Id: " + user.getFirst().user.id),
                    li("Name: " + user.getFirst().user.name),
                    li("Email: " + user.getFirst().user.email)
                ),
                br(),
                table(
                    sportsElements.toArray(new Element[0])
                ),
                br(),
                table(
                    activitiesElements.toArray(new Element[0])
                ),
                    br(),
                    a("/sports/"+ user.getFirst().sport.sid + "?skip=0&top=5", "Back to Sports Sid"),
                    br(),
                    a("/users/?skip=0&top=5", "Back to Users")
            )
        ).toString();
    }
}
