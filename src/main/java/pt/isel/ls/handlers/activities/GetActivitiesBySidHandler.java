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
import pt.isel.ls.results.activities.GetActivitiesBySidResult;
import pt.isel.ls.utils.TransactionManager;
import pt.isel.ls.utils.DataSource;

public class GetActivitiesBySidHandler implements RequestHandler {

    ActivitiesModel model = new ActivitiesModel();

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        HashMap<String, String> parameters = request.getParameters();
        HashMap<String, LinkedList<String>> queryString = request.getQueryStrings();

        if (parameters.containsKey("sid")) {

            javax.sql.DataSource dt = DataSource.getDataSource();
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
