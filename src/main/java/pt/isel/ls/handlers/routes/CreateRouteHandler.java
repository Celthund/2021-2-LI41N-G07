package pt.isel.ls.handlers.routes;

import java.util.Optional;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.models.RoutesModel;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.CreateRouteResult;
import pt.isel.ls.utils.TransactionManager;
import pt.isel.ls.utils.DataSource;

public class CreateRouteHandler implements RequestHandler {

    RoutesModel model = new RoutesModel();

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {

        if (request.getQueryStrings().containsKey("startlocation")
            && request.getQueryStrings().containsKey("endlocation")
            && request.getQueryStrings().containsKey("distance")
        ) {
            javax.sql.DataSource dt = DataSource.getDataSource();
            TransactionManager tm = new TransactionManager(dt);

            return Optional.of(tm.execute(conn -> {

                String startLocation = request.getQueryStrings().get("startlocation").getFirst();
                String endLocation = request.getQueryStrings().get("endlocation").getFirst();
                String distance = request.getQueryStrings().get("distance").getFirst();

                Route route = model.createRoute(startLocation, endLocation, distance, conn);
                if (route != null) {
                    return new CreateRouteResult(200, route,
                        "Route created with success with id = " + route.rid + "");
                }
                return new CreateRouteResult(500, null, "Failed to create route.");

            }));
        }
        throw new InvalidRequestException("Missing start location or end location or distance.");
    }
}
