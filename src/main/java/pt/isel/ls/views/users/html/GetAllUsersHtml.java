package pt.isel.ls.views.users.html;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetAllUsersResult;
import static pt.isel.ls.views.PageNavigation.getSkip;
import static pt.isel.ls.views.PageNavigation.getTop;
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

        HashMap<String, LinkedList<String>> queryString = requestResult.getRequest().getQueryStrings();
        LinkedList<Element> allElements = getFooter(queryString, users);

        if (users.size() > 0) {
            for (User user : users) {
                elements.add(tr(
                    td(alink("/users/" + user.id, Integer.toString(user.id))),
                    td(user.name),
                    td(user.email)
                ));
            }

            allElements.addFirst(table(
                elements.toArray(new Element[0])
            ));
        } else {
            allElements.addFirst(paragraph("No more results to show!"));
            allElements.addFirst(br());
        }

        allElements.addFirst(h1("Users"));
        allElements.addFirst(br());
        allElements.addFirst(alink("/", "Home Page"));

        Element html = html(
            head(
                title("Users")
            ),
            body(
                allElements.toArray(new Element[0])
            )
        );
        return html.toString();
    }

    private LinkedList<Element> getFooter(HashMap<String, LinkedList<String>> queryString, LinkedList<User> users) {
        LinkedList<Element> footer = new LinkedList<>();

        int skip = getSkip(queryString);
        int top = getTop(queryString);

        footer.add(br());

        if (skip > 0) {
            footer.add(alink("/users?skip=" + Math.max(0, (skip - top)) + "&top=" + Math.max(0, top), "Previous Page"));
        }

        if (top == users.size()) {
            footer.add(alink("/users?skip=" + (skip + top) + "&top=" + top, "Next Page"));
        }

        return footer;
    }
}
