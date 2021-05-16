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
import pt.isel.ls.results.activities.GetActivitiesBySidResult;

public class GetActivityBySidHandler implements RequestHandler {

    ActivitiesModel model = new ActivitiesModel();

    private GetActivitiesBySidResult getActivityBySid(String sid) throws AppException {
        LinkedList<Activity> activities = model.getActivityBySid(sid);
        if (activities != null) {
            return new GetActivitiesBySidResult(200, activities, "Found " + activities.size()
                + " activities with sid = " + sid);
        }
        return new GetActivitiesBySidResult(500, null, "Activity not found.");
    }

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        HashMap<String, String> parameters = request.getParameters();

        if (parameters.containsKey("sid")) {
            return Optional.of(getActivityBySid(parameters.get("sid")));
        }

        throw new InvalidRequestException();
    }
}
