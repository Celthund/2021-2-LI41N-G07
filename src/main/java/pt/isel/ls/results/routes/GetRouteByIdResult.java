package pt.isel.ls.results.routes;

import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.results.RequestResult;

public class GetRouteByIdResult implements RequestResult {
    public final int status;
    public final Route data;
    public final String message;

    public GetRouteByIdResult(int status, Route data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
