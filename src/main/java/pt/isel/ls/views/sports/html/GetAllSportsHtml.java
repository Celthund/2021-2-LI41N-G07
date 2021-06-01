package pt.isel.ls.views.sports.html;

import java.util.LinkedList;
import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.GetAllSportsResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;

public class GetAllSportsHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws InvalidJsonException {
        LinkedList<Sport> sports = ((GetAllSportsResult) requestResult).getData();
        LinkedList<Element> elements = new LinkedList<>();

        if (sports == null) {
            sports = new LinkedList<>();
        }

        elements.add(tr(
            th("Sid"),
            th("Name"),
            th("Description")

        ));

        for (Sport sport : sports) {
            elements.add(tr(
                td(a("/sports/" + sport.sid + "?skip=0&top=1", Integer.toString(sport.sid))),
                td(sport.name),
                td(sport.description)
            ));
        }


        Element html = html(
            head(
                title("Sports: ")

            ),
            body(
                    a("/", "HomePage"),
                br(),
                table(
                    elements.toArray(new Element[0])
                )
            )
        );
        return html.toString();
    }
}
