package pt.isel.ls.views.routes.html;

import java.util.HashSet;
import java.util.LinkedList;
import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.GetRouteByIdResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;
import static pt.isel.ls.views.builders.html.HtmlGetter.emptyDataSetHtml;

public class GetRouteByIdHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws InvalidJsonException {
        Route route = ((GetRouteByIdResult) requestResult).getData();

        if (route == null) {
            return emptyDataSetHtml(requestResult.getMessage()).toString();
        }

        LinkedList<Element> sportsElements = new LinkedList<>();

        // Add HashSet to show only each Sport once
        HashSet<Integer> sportsList = new HashSet<>();

        if (route.sports != null) {
            sportsElements.add(tr(
                th("Sport Id"),
                th("Name"),
                th("Description")
            ));
            for (Sport sport : route.sports) {
                if (!sportsList.contains(sport.sid)) {
                    sportsList.add(sport.sid);
                    sportsElements.add(tr(
                        td(alink("/sports/" + sport.sid, Integer.toString(sport.sid))),
                        td(sport.name),
                        td(sport.description)));
                }
            }
        }

        return html(
            head(
                title("Route" + route.rid)
            ),
            body(alink("/", "Home Page"),
                br(),
                h1("Route Id: " + route.rid),
                ul(
                    li("Id: " + route.rid),
                    li("Start Location: " + route.startLocation),
                    li("End Location: " + route.endLocation),
                    li("Distance: " + route.distance)
                ),
                table(
                    sportsElements.toArray(new Element[0])
                ),
                br(),
                alink("/routes?skip=0&top=5", "Back to Routes")
            )
        ).toString();
    }
}
