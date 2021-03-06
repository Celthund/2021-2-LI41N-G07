package pt.isel.ls.handlers.sports;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import javax.sql.DataSource;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.mappers.SportsMapper;
import pt.isel.ls.mappers.domainclasses.Sport;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.GetAllSportsResult;
import pt.isel.ls.databaseutils.Database;
import pt.isel.ls.databaseutils.TransactionManager;

public class GetAllSportsHandler implements RequestHandler {

    SportsMapper model = new SportsMapper();

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        DataSource dt = Database.getDataSource();
        TransactionManager tm = new TransactionManager(dt);

        return Optional.of(tm.execute(conn -> {
            HashMap<String, LinkedList<String>> queryStrings = request.getQueryStrings();
            String skip = queryStrings.containsKey("skip") ? queryStrings.get("skip").getFirst() : null;
            String top = queryStrings.containsKey("top") ? queryStrings.get("top").getFirst() : null;

            LinkedList<Sport> sports = model.getAllSports(skip, top, conn);
            if (sports.size() > 0) {
                return new GetAllSportsResult(200, sports, sports.size() + " sports found.");
            }
            return new GetAllSportsResult(404, null, "No sports found.");
        }));
    }
}