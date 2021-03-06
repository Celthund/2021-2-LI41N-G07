package pt.isel.ls.views.users.html;

import java.util.HashSet;
import java.util.LinkedList;
import pt.isel.ls.mappers.domainclasses.Activity;
import static pt.isel.ls.mappers.domainclasses.Activity.durationToString;
import pt.isel.ls.mappers.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetUserByIdResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;
import static pt.isel.ls.views.builders.html.HtmlGetter.emptyDataSetHtml;
import static pt.isel.ls.views.builders.html.HtmlGetter.getRouteHtmlTableRow;

public class GetUserByIdHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) {
        User user = ((GetUserByIdResult) requestResult).getData();

        if (user == null) {
            return emptyDataSetHtml(requestResult.getMessage()).toString();
        }


        LinkedList<Element> sportsElements = new LinkedList<>();
        sportsElements.add(tr(
            th("Sport Id"),
            th("Name"),
            th("Description")
        ));

        // Add HashSet to show only each Sport once
        HashSet<Integer> sportsList = new HashSet<>();

        // Gets all the activities of that user and through the activities of that user
        // shows the distinct sports the user made. With a link to that specific sport in the sport sid
        if (user.activities != null) {
            for (Activity activity : user.activities) {
                if (!sportsList.contains(activity.sport.sid)) {
                    sportsList.add(activity.sport.sid);
                    sportsElements.add(tr(
                        td(alink("/sports/" + activity.sport.sid, Integer.toString(activity.sport.sid))),
                        td(activity.sport.name),
                        td(activity.sport.description)
                    ));
                }
            }
        }


        LinkedList<Element> activitiesElements = new LinkedList<>();
        LinkedList<Element> routesElements;

        // Adds the table headers to the elements activitiesElements
        activitiesElements.add(tr(
            th("Activity"),
            th("Date"),
            th("Duration"),
            th("Start Location"),
            th("End Location"),
            th("Distance")
        ));


        // Adds the sports activities and routes(if its not null) to routesElements
        for (Activity activity : user.activities) {
            routesElements = new LinkedList<>();
            routesElements.add(td(alink("/sports/" + activity.sport.sid
                + "/activities/" + activity.aid, Integer.toString(activity.aid))));
            routesElements.add(td(activity.date.toString()));
            routesElements.add(td(durationToString(activity.duration)));

            if (activity.route != null) {
                routesElements.addAll(getRouteHtmlTableRow(activity.route));
            }
            activitiesElements.add(tr(
                routesElements.toArray((new Element[0]))
            ));
        }

        // Shows the all the it stores on the page
        return html(
            head(
                title("User: " + user.id),
                style()
            ),
            body(alink("/", "Home Page"),
                br(),
                h1("User: " + user.id),
                ul(
                    li("Id: " + user.id),
                    li("Name: " + user.name),
                    li("Email: " + user.email)
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
                alink("/users/?skip=0&top=5", "Back to Users")
            )
        ).toString();
    }
}
