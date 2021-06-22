package pt.isel.ls.handlers.activities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.models.ActivitiesModel;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivitiesByUidResult;
import pt.isel.ls.utils.TransactionManager;
import pt.isel.ls.utils.DataSource;

public class GetActivitiesByUidHandler implements RequestHandler {

    ActivitiesModel model = new ActivitiesModel();

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        HashMap<String, String> parameters = request.getParameters();

        if (parameters.containsKey("uid")) {
            javax.sql.DataSource dt = DataSource.getDataSource();
            TransactionManager tm = new TransactionManager(dt);

            return Optional.of(tm.execute(conn -> {
                String skip = null;
                if (request.getQueryStrings().containsKey("skip")) {
                    skip = request.getQueryStrings().get("skip").getFirst();
                }
                String top = null;
                if (request.getQueryStrings().containsKey("top")) {
                    top = request.getQueryStrings().get("top").getFirst();
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
