package pt.isel.ls.handlers.routes;

import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.models.RoutesModel;
import pt.isel.ls.request.Request;
import java.util.Optional;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.CreateRouteResult;

public class CreateRouteHandler implements RequestHandler {

    RoutesModel model = new RoutesModel();

    public CreateRouteResult createRoute(String startLocation, String endLocation, String distance)
            throws AppException {
        Route route = model.createRoute(startLocation, endLocation, distance);
        if (route != null) {
            return new CreateRouteResult(200, route, "Route created with success with id = " + route.rid + "");
        }
        return new CreateRouteResult(500, null, "Failed to create route.");
    }

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {

        if (request.getQueryStrings().containsKey("startlocation")
            && request.getQueryStrings().containsKey("endlocation")
            && request.getQueryStrings().containsKey("distance")
        ) {
            return Optional.of(createRoute(
                request.getQueryStrings().get("startlocation").getFirst(),
                request.getQueryStrings().get("endlocation").getFirst(),
                request.getQueryStrings().get("distance").getFirst()));
        }

        throw new InvalidRequestException();
    }
}
