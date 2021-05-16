package pt.isel.ls.handlers.routes;

import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.models.RoutesModel;
import pt.isel.ls.request.Request;
import java.util.LinkedList;
import java.util.Optional;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.GetAllRoutesResult;

public class GetAllRoutesHandler implements RequestHandler {

    RoutesModel model = new RoutesModel();


    public GetAllRoutesResult getAllRoutes(String skip, String top) throws AppException {
        LinkedList<Route> routes = model.getAllRoutes(skip, top);
        if (routes.size() > 0) {
            return new GetAllRoutesResult(200, routes, routes.size() + " routes found.");
        }
        return new GetAllRoutesResult(404, null, "No routes found.");
    }

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        if(request.getQueryStrings().containsKey("skip") && request.getQueryStrings().containsKey("top") )
            return  Optional.of(getAllRoutes(request.getQueryStrings().get("skip").getFirst(), request.getQueryStrings().get("top").getFirst()));

        return  Optional.of(getAllRoutes(null, null));
    }
}
