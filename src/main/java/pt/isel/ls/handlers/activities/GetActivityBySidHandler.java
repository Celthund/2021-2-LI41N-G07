package pt.isel.ls.handlers.activities;

import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.models.ActivitiesModel;
import pt.isel.ls.request.Request;
import java.util.HashMap;
import java.util.Optional;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivityBySidResult;

public class GetActivityBySidHandler implements RequestHandler {

    ActivitiesModel model = new ActivitiesModel();

    private GetActivityBySidResult getActivityBySid(String sid) throws AppException {
        Activity activity = model.getActivityBySid(sid);
        if (activity != null) {
            return new GetActivityBySidResult(200, activity, "Found activity with id = " + activity.aid);
        }
        return new GetActivityBySidResult(500, null, "Activity not found.");
    }

    @Override
    public Optional<RequestResult> execute(Request request) throws AppException {
        HashMap<String, String> parameters = request.getParameters();

        if (parameters.containsKey("sid")) {
            return Optional.of(getActivityBySid(parameters.get("sid")));
        }

        throw new InvalidRequestException();
    }
}
