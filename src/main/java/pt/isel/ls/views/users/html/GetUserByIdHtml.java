package pt.isel.ls.views.users.html;

import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetUserByIdResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;

import static pt.isel.ls.views.builders.html.HtmlBuilder.*;
import static pt.isel.ls.views.builders.html.HtmlBuilder.li;

public class GetUserByIdHtml implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) throws AppException {
        User user = ((GetUserByIdResult) requestResult).data;
        Element html =
                html(
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
                );
        return html.toString();
    }
}
