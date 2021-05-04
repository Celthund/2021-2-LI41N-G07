package pt.isel.ls.handlers.activities;

import pt.isel.ls.commands.RequestHandler;
import pt.isel.ls.commands.RequestResult;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.Models.domainclasses.Activity;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.Models.ActivitiesModel;
import pt.isel.ls.Request.Request;
import java.util.HashMap;
import java.util.Optional;

public class GetActivityByAidSidHandler implements RequestHandler {

    ActivitiesModel model = new ActivitiesModel();

    private RequestResult getActivityByAidSid(String aid, String sid) throws AppException {
        Activity activity = model.getActivityByAidSid(aid, sid);
        if (activity != null) {
            return new RequestResult(200, activity, "Found activity with id = " + activity.id);
        }
        return new RequestResult(500, null, "Activity not found.");
    }

    @Override
    public Optional<RequestResult> execute(Request request) throws AppException {
        HashMap<String, String> parameters = request.getParameters();

        if (parameters.containsKey("aid") && parameters.containsKey("sid")) {
            return Optional.of(getActivityByAidSid(parameters.get("aid"), parameters.get("sid")));
        }

        throw new InvalidRequestException();
    }
}
