package pt.isel.ls.views.users.html;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetAllUsersResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;

import java.util.LinkedList;

import static pt.isel.ls.views.builders.html.HtmlBuilder.*;

public class GetAllUsersHtml implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) throws InvalidJsonException {
        LinkedList<User> users = ((GetAllUsersResult) requestResult).data;
        LinkedList<Element> elements = new LinkedList<>();

        elements.add(tr(
                th("ID"),
                th("Name"),
                th("Email")

        ));

        for(User user : users) {
            elements.add(tr(
                    td(Integer.toString(user.id)),
                    td(user.name),
                    td(user.email)
            ));
        }


        Element html = html(
                head(
                        title("Users: ")

                ),
                body(
                        table(
                                elements.toArray(new Element[0])
                        )
                )
        );
        return html.toString();

    }

}
