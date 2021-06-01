package pt.isel.ls.views.routes.html;

import java.util.LinkedList;
import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.GetAllRoutesResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;

public class GetAllRoutesHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws InvalidJsonException {
        LinkedList<Route> routes = ((GetAllRoutesResult) requestResult).getData();
        LinkedList<Element> elements = new LinkedList<>();

        if (routes == null) {
            routes = new LinkedList<>();
        }

        elements.add(tr(
            th("Route Id"),
            th("Start Location"),
            th("End Location"),
            th("Distance")

        ));

        for (Route route : routes) {
            elements.add(tr(
                td(a("/router/" + route.rid + "?skip=0&top=1", Integer.toString(route.rid))),
                td(route.startLocation),
                td(route.endLocation),
                td(Integer.toString(route.distance))
            ));
        }

        Element html = html(
            head(
                title("Routes:")
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
