package pt.isel.ls.handlers.routes;

import pt.isel.ls.commands.RequestHandler;
import pt.isel.ls.commands.RequestResult;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.Models.domainclasses.Route;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.Models.RoutesModel;
import pt.isel.ls.Request.Request;
import java.util.Optional;

public class CreateRouteHandler implements RequestHandler {

    RoutesModel model = new RoutesModel();

    public RequestResult createRoute(String startLocation, String endLocation, String distance) throws AppException {
        Route route = model.createRoute(startLocation, endLocation, distance);
        if (route != null) {
            return new RequestResult(200, route, "Route created with success with id = " + route.rid + "");
        }
        return new RequestResult(500, null, "Failed to create route.");
    }

    @Override
    public Optional<RequestResult> execute(Request request) throws AppException {

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
