package pt.isel.ls.handlers.activities;

import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.models.ActivitiesModel;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivitiesByUidResult;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

public class GetActivitiesByUidHandler implements RequestHandler {

    ActivitiesModel model = new ActivitiesModel();

    private GetActivitiesByUidResult getActivitiesByUid(String uid, String skip, String top) throws AppException {
        LinkedList<Activity> activities = model.getActivitiesByUid(uid, skip, top);

        if (activities != null) {
            return new GetActivitiesByUidResult(200, activities, "Found " + activities.size()
                    + " activities with uid = " + uid);
        }

        return new GetActivitiesByUidResult(404, null, "Activity not found");
    }


    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        HashMap<String, String> parameters = request.getParameters();
        if (parameters.containsKey("uid")) {
            return Optional.of(getActivitiesByUid(parameters.get("uid"), parameters.get("skip"), parameters.get("top")));
        }
        throw new InvalidRequestException();
    }
}