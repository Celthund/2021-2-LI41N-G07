package pt.isel.ls.views.routes.json;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.CreateRouteResult;
import pt.isel.ls.views.View;
import static pt.isel.ls.views.builders.json.JsonGetter.*;

public class CreateRouteJson implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) throws InvalidJsonException {
        Route route = ((CreateRouteResult) requestResult).getData();
        return getRouteJson(route).toString();
    }
}
