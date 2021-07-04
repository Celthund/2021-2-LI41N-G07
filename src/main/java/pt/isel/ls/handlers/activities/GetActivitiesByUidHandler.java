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
import pt.isel.ls.results.activities.GetActivitiesByUidResult;
import pt.isel.ls.dabataseutils.Database;
import pt.isel.ls.dabataseutils.TransactionManager;

public class GetActivitiesByUidHandler implements RequestHandler {

    ActivitiesMapper model = new ActivitiesMapper();

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        HashMap<String, String> parameters = request.getParameters();
        HashMap<String, LinkedList<String>> queryStrings = request.getQueryStrings();

        if (parameters.containsKey("uid")) {
            DataSource dt = Database.getDataSource();
            TransactionManager tm = new TransactionManager(dt);

            return Optional.of(tm.execute(conn -> {
                String skip = null;
                if (queryStrings.containsKey("skip")) {
                    skip = queryStrings.get("skip").getFirst();
                }
                String top = null;
                if (queryStrings.containsKey("top")) {
                    top = queryStrings.get("top").getFirst();
                }

                String uid = parameters.get("uid");
                LinkedList<Activity> activities = model.getActivitiesByUid(uid, skip, top, conn);

                if (activities != null) {
                    return new GetActivitiesByUidResult(200, activities, "Found " + activities.size()
                        + " activities with uid = " + uid);
                }
                return new GetActivitiesByUidResult(404, null, "Activity not found");
            }));
        }
        throw new InvalidRequestException("Missing user id.");
    }
}
