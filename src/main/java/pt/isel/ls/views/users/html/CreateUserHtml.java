package pt.isel.ls.views.users.html;

import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.CreateUserResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;

public class CreateUserHtml implements View {

    @Override
    public String getRepresentation(RequestResult requestResult) {
        User user = ((CreateUserResult) requestResult).data;
        Element html =
            html(
                head(
                    title("User " + user.id)
                ),
                body(
                    h1("User ID: " + user.id),
                    dl(
                        dt("id: " + user.id),
                        dt("name: " + user.name),
                        dt("email: " + user.email)
                    )
                )
            );
        return html.toString();
    }
}
