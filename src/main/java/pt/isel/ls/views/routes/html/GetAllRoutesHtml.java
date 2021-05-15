package pt.isel.ls.views.routes.html;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.GetAllRoutesResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;

import java.util.LinkedList;

import static pt.isel.ls.views.builders.html.HtmlBuilder.*;

public class GetAllRoutesHtml implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) throws InvalidJsonException {
        LinkedList<Route> routes = ((GetAllRoutesResult) requestResult).data;
        LinkedList<Element> elements = new LinkedList<>();

        elements.add(tr(
                th("SID"),
                th("StartLocation"),
                th("EndLocation"),
                th("Distance")

        ));

        for(Route route : routes) {
            elements.add(tr(
                    td(Integer.toString(route.rid)),
                    td(route.startLocation),
                    td(route.endLocation),
                    td(Integer.toString(route.distance))
            ));
        }


        Element html = html(
                head(
                        title("Routes: ")

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