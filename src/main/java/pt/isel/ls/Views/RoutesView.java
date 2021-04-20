package pt.isel.ls.Views;

import pt.isel.ls.Commands.RequestHandler;
import pt.isel.ls.Commands.RequestResult;
import pt.isel.ls.DataClass.Route;
import pt.isel.ls.Exceptions.InvalidRequestException;
import pt.isel.ls.Models.RoutesModel;
import pt.isel.ls.Request.Method;
import pt.isel.ls.Request.Request;

import java.util.LinkedList;

public class RoutesView implements RequestHandler {

    RoutesModel model = new RoutesModel();

    public RequestResult getRouteById(String id) {
        Route route = model.getRouteById(id);
        if (route != null){
            return new RequestResult(
                    200,
                    route,
                    "Found route with id = " + id);
        }
        return new RequestResult(404, null, "Route not found.");
    }

    public RequestResult getAllRoutes() {
        LinkedList<Route> routes = model.getAllRoutes();
        if(routes.size() > 0){
            return new RequestResult(200, routes, routes.size() + " routes found.");
        }
        return new RequestResult(404, null, "No routes found.");
    }

    public RequestResult createRoute(String startLocation, String endLocation, String distance) {
        Route route = model.createRoute(startLocation, endLocation, distance);
        if (route != null){
            return new RequestResult(200, route, "Route created with success with id = " + route.id + "");
        }
        return new RequestResult(500, null, "Failed to create route.");
    }

    @Override
    public RequestResult execute(Request request) throws InvalidRequestException {
        if (request.getMethod() == Method.GET && request.getParameters().size() == 0)
            return getAllRoutes();

        if (request.getMethod() == Method.GET && request.getParameters().containsKey("rid"))
            return getRouteById(request.getParameters().get("rid"));

        if (request.getMethod() == Method.POST &&
                request.getQueryString().containsKey("startLocation") &&
                request.getQueryString().containsKey("endLocation") &&
                request.getQueryString().containsKey("distance")
        )
            return createRoute(
                    request.getQueryString().get("startLocation").getFirst(),
                    request.getQueryString().get("endLocation").getFirst(),
                    request.getQueryString().get("distance").getFirst());

        throw new InvalidRequestException();
    }
}
