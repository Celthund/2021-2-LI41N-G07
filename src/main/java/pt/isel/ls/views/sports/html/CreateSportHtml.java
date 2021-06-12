package pt.isel.ls.views.sports.html;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.CreateSportResult;
import pt.isel.ls.views.View;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;

public class CreateSportHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws InvalidJsonException {
        Sport sport = ((CreateSportResult) requestResult).getData();

        if (sport == null) {
            return html().toString();
        }

        return html(
            head(
                title("Sport " + sport.sid)
            ),
            body(
                h1("Sport ID " + sport.sid),
                ul(
                    li("Id " + sport.sid),
                    li("Name: " + sport.name),
                    li("Description: " + sport.description)
                )
            )
        ).toString();
    }
}
