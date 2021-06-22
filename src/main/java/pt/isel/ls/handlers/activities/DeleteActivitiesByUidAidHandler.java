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
import pt.isel.ls.results.activities.DeleteActivitiesByUidAidResult;
import pt.isel.ls.utils.TransactionManager;
import pt.isel.ls.utils.DataSource;

public class DeleteActivitiesByUidAidHandler implements RequestHandler {

    ActivitiesModel model = new ActivitiesModel();

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        HashMap<String, LinkedList<String>> queryString = request.getQueryStrings();
        HashMap<String, String> parameters = request.getParameters();
        if (parameters.containsKey("uid") && queryString.containsKey("activity")) {
            javax.sql.DataSource dt = DataSource.getDataSource();
            TransactionManager tm = new TransactionManager(dt);

            return Optional.of(tm.execute(conn -> {
                String uid = parameters.get("uid");
                LinkedList<String> aid = queryString.get("activity");
                LinkedList<Activity> activities = model.deleteActivity(uid, aid, conn);

                if (activities != null) {
                    return new DeleteActivitiesByUidAidResult(200, activities, "Deleted with success "
                        + activities.size() + ((activities.size() == 1) ? "Activity" : "Activities"));
                }
                return new DeleteActivitiesByUidAidResult(500, null,
                    "Failed to delete any activity.");

            }));
        }
        throw new InvalidRequestException("Missing user id or activity id.");
    }
}
