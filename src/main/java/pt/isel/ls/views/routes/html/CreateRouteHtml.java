package pt.isel.ls.views.routes.html;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.mappers.domainclasses.Route;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.CreateRouteResult;
import pt.isel.ls.views.View;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;
import static pt.isel.ls.views.builders.html.HtmlGetter.emptyDataSetHtml;

public class CreateRouteHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws InvalidJsonException {
        Route route = ((CreateRouteResult) requestResult).getData();

        if (route == null) {
            return emptyDataSetHtml(requestResult.getMessage()).toString();
        }

        return html(
            head(
                title("Route" + route.rid)
            ),
            body(
                h1("Route Id: " + route.rid),
                ul(
                    li("Id: " + route.rid),
                    li("Start Location: " + route.startLocation),
                    li("End Location: " + route.endLocation),
                    li("Distance: " + route.distance)
                )
            )
        ).toString();
    }
}
