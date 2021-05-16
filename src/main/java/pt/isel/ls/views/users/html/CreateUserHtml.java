package pt.isel.ls.views.users.html;

import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.CreateUserResult;
import pt.isel.ls.views.View;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;
import static pt.isel.ls.views.builders.html.HtmlGetter.emptyDataSetHtml;

public class CreateUserHtml implements View {

    @Override
    public String getRepresentation(RequestResult<?> requestResult) {
        User user = ((CreateUserResult) requestResult).getData();

        if (user == null) {
            return emptyDataSetHtml(requestResult.getMessage()).toString();
        }
        return html(
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
        ).toString();
    }
}