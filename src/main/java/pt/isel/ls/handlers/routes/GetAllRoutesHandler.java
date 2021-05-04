package pt.isel.ls.handlers.routes;

import pt.isel.ls.commands.RequestHandler;
import pt.isel.ls.commands.RequestResult;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.Models.domainclasses.Route;
import pt.isel.ls.Models.RoutesModel;
import pt.isel.ls.Request.Request;
import java.util.LinkedList;
import java.util.Optional;

public class GetAllRoutesHandler implements RequestHandler {

    RoutesModel model = new RoutesModel();

    public RequestResult getAllRoutes() throws AppException {
        LinkedList<Route> routes = model.getAllRoutes();
        if (routes.size() > 0) {
            return new RequestResult(200, routes, routes.size() + " routes found.");
        }
        return new RequestResult(404, null, "No routes found.");
    }


    @Override
    public Optional<RequestResult> execute(Request request) throws AppException {
        return Optional.of(getAllRoutes());
    }
}
