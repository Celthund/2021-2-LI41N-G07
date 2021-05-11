package pt.isel.ls.handlers.routes;

import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.models.RoutesModel;
import pt.isel.ls.request.Request;
import java.util.Optional;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.GetRouteByIdResult;

public class GetRouteByIdHandler implements RequestHandler {

    RoutesModel model = new RoutesModel();

    public GetRouteByIdResult getRouteById(String id) throws AppException {
        Route route = model.getRouteById(id);
        if (route != null) {
            return new GetRouteByIdResult(
                200,
                route,
                "Found route with id = " + id);
        }
        return new GetRouteByIdResult(404, null, "Route not found.");
    }


    @Override
    public Optional<RequestResult> execute(Request request) throws AppException {
        if (request.getParameters().containsKey("rid")) {
            return Optional.of(getRouteById(request.getParameters().get("rid")));
        }

        throw new InvalidRequestException();
    }
}
