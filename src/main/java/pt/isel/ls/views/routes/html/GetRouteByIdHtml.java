package pt.isel.ls.views.routes.html;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.GetRouteByIdResult;
import pt.isel.ls.views.View;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;
import static pt.isel.ls.views.builders.html.HtmlGetter.emptyDataSetHtml;

public class GetRouteByIdHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws InvalidJsonException {
        Route route = ((GetRouteByIdResult) requestResult).getData();

        if (route == null) {
            return emptyDataSetHtml(requestResult.getMessage()).toString();
        }

        return html(
            head(
                title("Route " + route.rid)
            ),
            body(
                h1("Route ID: " + route.rid),
                ul(
                    li("id: " + route.rid),
                    li("StartLocation: " + route.startLocation),
                    li("EndLocation: " + route.endLocation),
                    li("Distance: " + route.distance)
                )
            )
        ).toString();
    }
}
