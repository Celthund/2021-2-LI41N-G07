package pt.isel.ls.handlers.routes;

import java.util.LinkedList;
import java.util.Optional;
import javax.sql.DataSource;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.mappers.RoutesMapper;
import pt.isel.ls.mappers.SportsMapper;
import pt.isel.ls.mappers.domainclasses.Route;
import pt.isel.ls.mappers.domainclasses.Sport;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.GetRouteByIdResult;
import pt.isel.ls.databaseutils.Database;
import pt.isel.ls.databaseutils.TransactionManager;

public class GetRouteByIdHandler implements RequestHandler {

    RoutesMapper model = new RoutesMapper();
    SportsMapper modelSport = new SportsMapper();

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        if (request.getParameters().containsKey("rid")) {
            DataSource dt = Database.getDataSource();
            TransactionManager tm = new TransactionManager(dt);

            return Optional.of(tm.execute(conn -> {
                String rid = request.getParameters().get("rid");
                Route route = model.getRouteById(rid, conn);

                if (route != null) {
                    // Gets and stores the sport that corresponds to the route we currently are handling
                    LinkedList<Sport> sports = modelSport.getSportsByRid(rid, conn);
                    route.setSports(sports);

                    return new GetRouteByIdResult(
                        200,
                        route,
                        "Found route with id = " + rid);
                }
                return new GetRouteByIdResult(404, null, "Route not found.");
            }));
        }
        throw new InvalidRequestException("Missing route id.");
    }
}
