package pt.isel.ls.handlers.routes;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import javax.sql.DataSource;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.RoutesModel;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.GetAllRoutesResult;
import pt.isel.ls.utils.Database;
import pt.isel.ls.utils.TransactionManager;

public class GetAllRoutesHandler implements RequestHandler {

    RoutesModel model = new RoutesModel();

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        DataSource dt = Database.getDataSource();
        TransactionManager tm = new TransactionManager(dt);

        return Optional.of(tm.execute(conn -> {
            HashMap<String, LinkedList<String>> queryStrings = request.getQueryStrings();
            String skip = queryStrings.containsKey("skip") ? request.getQueryStrings().get("skip").getFirst() : null;
            String top = queryStrings.containsKey("top") ? request.getQueryStrings().get("top").getFirst() : null;

            LinkedList<Route> routes = model.getAllRoutes(skip, top, conn);
            if (routes.size() > 0) {
                return new GetAllRoutesResult(200, routes, routes.size() + " routes found.");
            }
            return new GetAllRoutesResult(404, null, "No routes found.");
        }));
    }
}
