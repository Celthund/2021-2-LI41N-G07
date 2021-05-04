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

public class GetActivityByUidHandler implements RequestHandler {

    ActivitiesModel model = new ActivitiesModel();

    private RequestResult getActivityByUid(String uid) throws AppException {
        Activity activity = model.getActivityByUid(uid);

        if (activity != null) {
            return new RequestResult(200, activity, "Found user activity with id = " + activity.id);
        }

        return new RequestResult(404, null, "Activity not found");
    }


    @Override
    public Optional<RequestResult> execute(Request request) throws AppException {
        HashMap<String, String> parameters = request.getParameters();
        if (request.getPath()[0].equals("users") && parameters.containsKey("uid")) {
            return Optional.of(getActivityByUid(parameters.get("uid")));
        }
        throw new InvalidRequestException();
    }
}
