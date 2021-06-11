package pt.isel.ls.views.sports.html;

import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.CreateSportResult;
import pt.isel.ls.results.sports.GetSportByIdResult;
import pt.isel.ls.views.View;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;
import static pt.isel.ls.views.builders.html.HtmlGetter.emptyDataSetHtml;
import static pt.isel.ls.views.builders.json.JsonGetter.emptyDataSetJson;

public class GetSportByIdHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws AppException {
        Sport sport = ((GetSportByIdResult) requestResult).getData();

        if (sport == null) {
            return emptyDataSetHtml(requestResult.getMessage()).toString();
        }

        return html(
            head(
                title("Sport " + sport.sid)
            ),
            body(
                h1("Sport Id" + sport.sid),
                ul(
                    li("Id " + sport.sid),
                    li("Name: " + sport.name),
                    li("Description: " + sport.description)
                ),
                    br(),
                    a("/sports/"+ sport.sid +"/activities?skip=0&top=5", "Sports Activities"),
                    br(),
                    a("/sports/?skip=0&top=5", "Back to Sports")
            )
        ).toString();
    }
}
