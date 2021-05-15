package pt.isel.ls.views.sports.html;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.GetAllSportsResult;
import pt.isel.ls.results.users.GetAllUsersResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;

import java.util.LinkedList;

import static pt.isel.ls.views.builders.html.HtmlBuilder.*;

public class GetAllSportsHtml implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) throws InvalidJsonException {
        LinkedList<Sport> sports = ((GetAllSportsResult) requestResult).data;
        LinkedList<Element> elements = new LinkedList<>();

        elements.add(tr(
                th("SID"),
                th("Name"),
                th("Description")

        ));

        for(Sport sport : sports) {
            elements.add(tr(
                    td(Integer.toString(sport.sid)),
                    td(sport.name),
                    td(sport.description)
            ));
        }


        Element html = html(
                head(
                        title("Sports: ")

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
