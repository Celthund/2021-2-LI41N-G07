package pt.isel.ls.results.routes;

import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.results.RequestResult;

public class GetRouteByIdResult extends RequestResult<Route> {
    public GetRouteByIdResult(int status, Route data, String message) {
        super(status, data, message);
    }
}
