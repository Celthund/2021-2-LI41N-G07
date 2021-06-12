package pt.isel.ls.views.users.html;

import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetAllUsersResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;

import java.util.HashMap;
import java.util.LinkedList;

import static pt.isel.ls.views.PageNavigation.getSkip;
import static pt.isel.ls.views.PageNavigation.getTop;
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

        HashMap<String, LinkedList<String>> queryString = requestResult.getRequest().getQueryStrings();
        LinkedList<Element> allElements = getFooter(queryString, users);
        allElements.addFirst(table(
                elements.toArray(new Element[0])
        ));
        allElements.addFirst(h1("Users"));
        allElements.addFirst(br());
        allElements.addFirst(a("/", "Home Page"));

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
            footer.add(a("/users?skip=" + Math.max(0, (skip - top)) + "&top=" + Math.max(0, top), "Previous Page"));
        }

        if (top == users.size()) {
            footer.add(a("/users?skip=" + (skip + top) + "&top=" + top, "Next Page"));
        }

        return footer;
    }
}
