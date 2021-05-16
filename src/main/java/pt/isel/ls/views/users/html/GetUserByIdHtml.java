package pt.isel.ls.views.users.html;

import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetUserByIdResult;
import pt.isel.ls.views.View;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;
import static pt.isel.ls.views.builders.html.HtmlGetter.emptyDataSetHtml;

public class GetUserByIdHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) {
        User user = ((GetUserByIdResult) requestResult).getData();

        if (user == null)
            return emptyDataSetHtml(requestResult.getMessage()).toString();

        return html(
                head(
                        title("User " + user.id)
                ),
                body(
                        h1("User ID: " + user.id),
                        ul(
                                li("id: " + user.id),
                                li("name: " + user.name),
                                li("email: " + user.email)
                        )
                )
        ).toString();
    }
}
