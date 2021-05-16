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

public class GetActivityByAidSidHandler implements RequestHandler {

    ActivitiesModel model = new ActivitiesModel();

    private GetActivityByAidSidResult getActivityByAidSid(String aid, String sid) throws AppException {
        Activity activity = model.getActivityByAidSid(aid, sid);
        if (activity != null) {
            return new GetActivityByAidSidResult(200, activity, "Found activity with id = " + activity.aid);
        }
        return new GetActivityByAidSidResult(500, null, "Activity not found.");
    }

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        HashMap<String, String> parameters = request.getParameters();

        if (parameters.containsKey("aid") && parameters.containsKey("sid")) {
            return Optional.of(getActivityByAidSid(parameters.get("aid"), parameters.get("sid")));
        }

        throw new InvalidRequestException();
    }
}
