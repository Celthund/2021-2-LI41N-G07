package pt.isel.ls.views.routes.plaintext;

import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.CreateRouteResult;
import pt.isel.ls.views.View;

import static pt.isel.ls.views.builders.plaintext.PlainTextGetter.getRoutePlainText;

public class CreateRoutePlainText implements View {

    @Override
    public String getRepresentation(RequestResult requestResult) {
        Route route = ((CreateRouteResult) requestResult).getData();
        return getRoutePlainText(route);
    }
}
