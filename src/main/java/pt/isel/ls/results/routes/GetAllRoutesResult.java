package pt.isel.ls.results.routes;

import java.util.LinkedList;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.results.RequestResult;

public class GetAllRoutesResult implements RequestResult {
    public final int status;
    public final LinkedList<Route> data;
    public final String message;

    public GetAllRoutesResult(int status, LinkedList<Route> data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
