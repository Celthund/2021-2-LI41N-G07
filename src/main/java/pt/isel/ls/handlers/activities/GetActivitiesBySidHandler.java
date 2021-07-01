package pt.isel.ls.handlers.activities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import javax.sql.DataSource;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.mappers.ActivitiesMapper;
import pt.isel.ls.mappers.domainclasses.Activity;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivitiesBySidResult;
import pt.isel.ls.dabataseutils.Database;
import pt.isel.ls.dabataseutils.TransactionManager;

public class GetActivitiesBySidHandler implements RequestHandler {

    ActivitiesMapper model = new ActivitiesMapper();

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        HashMap<String, String> parameters = request.getParameters();
        HashMap<String, LinkedList<String>> queryString = request.getQueryStrings();

        if (parameters.containsKey("sid")) {
            DataSource dt = Database.getDataSource();
            TransactionManager tm = new TransactionManager(dt);

            return Optional.of(tm.execute(conn -> {
                String skip = queryString.containsKey("skip") ? queryString.get("skip").getFirst() : null;
                String top = queryString.containsKey("top") ? queryString.get("top").getFirst() : null;
                String sid = parameters.get("sid");
                LinkedList<Activity> activities = model.getActivitiesBySid(sid, skip, top, conn);
                if (activities != null) {
                    return new GetActivitiesBySidResult(200, activities, "Found " + activities.size()
                        + " activities with sid = " + sid);
                }
                return new GetActivitiesBySidResult(500, null, "Activity not found.");
            }));
        }
        throw new InvalidRequestException("Missing sport id.");
    }
}
