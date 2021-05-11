package pt.isel.ls.results.routes;

import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.results.RequestResult;

public class CreateRouteResult implements RequestResult {
    public final int status;
    public final Route data;
    public final String message;

    public CreateRouteResult(int status, Route data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
