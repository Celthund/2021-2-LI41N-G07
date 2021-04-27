package pt.isel.ls.Views;

import pt.isel.ls.Commands.RequestHandler;
import pt.isel.ls.Commands.RequestResult;
import pt.isel.ls.DataClass.Route;
import pt.isel.ls.Exceptions.InvalidRequestException;
import pt.isel.ls.Exceptions.ServerErrorException;
import pt.isel.ls.Models.RoutesModel;
import pt.isel.ls.Request.Method;
import pt.isel.ls.Request.Request;

import java.util.LinkedList;

public class RoutesView implements RequestHandler {

    RoutesModel model = new RoutesModel();

    public RequestResult getRouteById(String id) throws ServerErrorException {
        Route route = model.getRouteById(id);
        if (route != null){
            return new RequestResult(
                    200,
                    route,
                    "Found route with id = " + id);
        }
        return new RequestResult(404, null, "Route not found.");
    }

    public RequestResult getAllRoutes() throws ServerErrorException {
        LinkedList<Route> routes = model.getAllRoutes();
        if(routes.size() > 0){
            return new RequestResult(200, routes, routes.size() + " routes found.");
        }
        return new RequestResult(404, null, "No routes found.");
    }

    public RequestResult createRoute(String startLocation, String endLocation, String distance) throws ServerErrorException {
        Route route = model.createRoute(startLocation, endLocation, distance);
        if (route != null){
            return new RequestResult(200, route, "Route created with success with id = " + route.rid + "");
        }
        return new RequestResult(500, null, "Failed to create route.");
    }

    // The execute method receives a request, checks the arguments in it so it can get the query correspondent to the Method, Path and/or parameters in the Request class
    @Override
    public RequestResult execute(Request request) throws InvalidRequestException, ServerErrorException {
        if (request.getMethod() == Method.GET && request.getParameters().size() == 0)
            return getAllRoutes();

        if (request.getMethod() == Method.GET && request.getParameters().containsKey("rid"))
            return getRouteById(request.getParameters().get("rid"));

        if (request.getMethod() == Method.POST &&
                request.getQueryStrings().containsKey("startlocation") &&
                request.getQueryStrings().containsKey("endlocation") &&
                request.getQueryStrings().containsKey("distance")
        )
            return createRoute(
                    request.getQueryStrings().get("startlocation").getFirst(),
                    request.getQueryStrings().get("endlocation").getFirst(),
                    request.getQueryStrings().get("distance").getFirst());

        throw new InvalidRequestException();
    }
}
