package pt.isel.ls.views.users.html;

import java.util.LinkedList;
import java.util.logging.Handler;

import pt.isel.ls.handlers.activities.GetActivitiesByUidHandler;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetAllUsersResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;

public class GetAllUsersHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) {
        LinkedList<User> users = ((GetAllUsersResult) requestResult).getData();
        LinkedList<Element> elements = new LinkedList<>();

        if (users == null) {
            users = new LinkedList<>();
        }

        elements.add(tr(
            th("Id"),
            th("Name"),
            th("Email")

        ));

        for (User user : users) {
            elements.add(tr(
                td(a("/users/" + user.id, Integer.toString(user.id))),
                td(user.name),
                td(user.email)
            ));
        }

        Element html = html(
            head(
                title("Users")

            ),
            body(
                    a("/", "HomePage"),
                br(),
                table(
                    elements.toArray(new Element[0])
                )
            )
        );
        return html.toString();

    }
}
