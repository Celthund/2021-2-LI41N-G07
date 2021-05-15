package pt.isel.ls.views.sports.html;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.CreateSportResult;
import pt.isel.ls.results.users.CreateUserResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;

import static pt.isel.ls.views.builders.html.HtmlBuilder.*;
import static pt.isel.ls.views.builders.html.HtmlBuilder.li;

public class CreateSportHtml implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) throws InvalidJsonException {
        Sport sport = ((CreateSportResult) requestResult).getData();
        Element html =
                html(
                        head(
                                title("Sport " + sport.sid)
                        ),
                        body(
                                h1("Sport ID" + sport.sid),
                                ul(
                                        li("id " + sport.sid),
                                        li("Name: " + sport.name),
                                        li("Email: " + sport.description)
                                )
                        )
                );
        return html.toString();
    }
}
