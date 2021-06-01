package pt.isel.ls.views.users.html;

import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetUserByIdResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;
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

        LinkedList<Element> elements1 = new LinkedList<>();
        elements1.add(tr(
                th("Sid"),
                th("Name"),
                th("Description")

        ));

        for (Activity activity : user) {
            elements1.add(tr(
                    td(a("/sports/" + activity.sport.sid
                            + "?skip=0&top=1", Integer.toString(activity.sport.sid))),
                    td(activity.sport.name),
                    td(activity.sport.description)
            ));
        }


        LinkedList<Element> elements2 = new LinkedList<>();
        for (Activity activity : user) {
            elements2.add(td(a("/sports/" + activity.sport.sid
                    + "/activities/" + activity.aid
                    + "?skip=0&top=1", Integer.toString(activity.aid))));
            elements2.add(td(activity.date.toString()));
            elements2.add(td(durationToString(activity.duration)));

            if (activity.route != null) {
                elements2.addAll(getRouteHtmlTableRow(activity.route));
            }
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
                    elements1.toArray(new Element[0])
                ),
                br(),
                table(
                    elements2.toArray(new Element[0])
                ),
                    a("/users?skip=0&top=1", "Back to Users")
            )
        ).toString();
    }
}
