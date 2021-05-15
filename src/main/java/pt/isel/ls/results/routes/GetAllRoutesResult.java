package pt.isel.ls.results.routes;

import java.util.LinkedList;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.results.RequestResult;

public class GetAllRoutesResult extends RequestResult<LinkedList<Route>> {

    public GetAllRoutesResult(int status, LinkedList<Route> data, String message) {
        super(status, data, message);
    }
}
