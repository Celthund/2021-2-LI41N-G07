package pt.isel.ls.handlers.activities;

import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.models.ActivitiesModel;
import pt.isel.ls.request.Request;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivityByUidResult;

public class GetActivityByUidHandler implements RequestHandler {

    ActivitiesModel model = new ActivitiesModel();

    private GetActivityByUidResult getActivityByUid(String uid) throws AppException {
        LinkedList<Activity> activities = model.getActivitiesByUid(uid);

        if (activities != null) {
            return new GetActivityByUidResult(200, activities, "Found "+ activities.size()
                    + " activities with uid = " + uid);
        }

        return new GetActivityByUidResult(404, null, "Activity not found");
    }


    @Override
    public Optional<RequestResult> execute(Request request) throws AppException {
        HashMap<String, String> parameters = request.getParameters();
        if (parameters.containsKey("uid")) {
            return Optional.of(getActivityByUid(parameters.get("uid")));
        }
        throw new InvalidRequestException();
    }
}
