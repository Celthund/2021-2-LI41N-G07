package pt.isel.ls.results.routes;

import pt.isel.ls.mappers.domainclasses.Route;
import pt.isel.ls.results.RequestResult;

public class CreateRouteResult extends RequestResult<Route> {
    public CreateRouteResult(int status, Route data, String message) {
        super(status, data, message);
    }
}
