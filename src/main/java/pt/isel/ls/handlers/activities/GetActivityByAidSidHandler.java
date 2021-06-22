package pt.isel.ls.handlers.activities;

import java.util.HashMap;
import java.util.Optional;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.models.ActivitiesModel;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivityByAidSidResult;
import pt.isel.ls.utils.TransactionManager;
import pt.isel.ls.utils.DataSource;

public class GetActivityByAidSidHandler implements RequestHandler {

    ActivitiesModel model = new ActivitiesModel();

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        HashMap<String, String> parameters = request.getParameters();

        if (parameters.containsKey("aid") && parameters.containsKey("sid")) {
            javax.sql.DataSource dt = DataSource.getDataSource();
            TransactionManager tm = new TransactionManager(dt);

            return Optional.of(tm.execute(conn -> {
                String aid = parameters.get("aid");
                String sid = parameters.get("sid");
                Activity activity = model.getActivityByAidSid(aid, sid, conn);
                if (activity != null) {
                    return new GetActivityByAidSidResult(200, activity, "Found activity with id = " + activity.aid);
                }
                return new GetActivityByAidSidResult(500, null, "Activity not found.");
            }));
        }
        throw new InvalidRequestException("Missing activity id or sport id.");
    }
}
