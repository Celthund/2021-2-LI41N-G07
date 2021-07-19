package pt.isel.ls.handlers.routes;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import javax.sql.DataSource;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.mappers.RoutesMapper;
import pt.isel.ls.mappers.domainclasses.Route;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.CreateRouteResult;
import pt.isel.ls.databaseutils.Database;
import pt.isel.ls.databaseutils.TransactionManager;

public class CreateRouteHandler implements RequestHandler {

    RoutesMapper model = new RoutesMapper();

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {

        HashMap<String, LinkedList<String>> queryStrings = request.getQueryStrings();

        if (queryStrings.containsKey("startlocation")
            && queryStrings.containsKey("endlocation")
            && queryStrings.containsKey("distance")
        ) {
            DataSource dt = Database.getDataSource();
            TransactionManager tm = new TransactionManager(dt);

            return Optional.of(tm.execute(conn -> {

                String startLocation = queryStrings.get("startlocation").getFirst();
                String endLocation = queryStrings.get("endlocation").getFirst();
                String distance = queryStrings.get("distance").getFirst();

                Route route = model.createRoute(startLocation, endLocation, distance, conn);
                if (route != null) {
                    CreateRouteResult resp = new CreateRouteResult(303, route,
                        "Route created with success with id = " + route.rid + "");
                    resp.addHeader("Location", "/routes/" + route.rid);
                    return resp;
                }
                return new CreateRouteResult(500, null, "Failed to create route.");
            }));
        }
        throw new InvalidRequestException("Missing start location or end location or distance.");
    }
}
