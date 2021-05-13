package pt.isel.ls.views.users.json.routes;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.GetRouteByIdResult;

public class GetRouteByIdJson extends AbstractRouteJson {
    @Override
    public String getRepresentation(RequestResult requestResult) throws InvalidJsonException {
        Route route = ((GetRouteByIdResult) requestResult).data;
        return getRouteJson(route).toString();
    }
}
