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
import pt.isel.ls.results.activities.CreateActivityResult;

public class CreateActivityHandler implements RequestHandler {

    ActivitiesModel model = new ActivitiesModel();

    private CreateActivityResult createActivity(String sid, String uid, String duration, String date, String rid)
            throws AppException {
        Activity activity = model.createActivity(sid, uid, duration, date, rid);

        if (activity != null) {
            return new CreateActivityResult(200, activity, "Activity created with success with id = " + activity.id);
        }
        return new CreateActivityResult(500, null, "Failed to create activity.");
    }

    @Override
    public Optional<RequestResult> execute(Request request) throws AppException {
        HashMap<String, LinkedList<String>> queryString = request.getQueryStrings();
        HashMap<String, String> parameters = request.getParameters();
        if (parameters.containsKey("sid")
                && queryString.containsKey("uid")
                && queryString.containsKey("duration")
                && queryString.containsKey("date")) {
            String sid = parameters.get("sid");
            String uid = queryString.get("uid").getFirst();
            String duration = queryString.get("duration").getFirst();
            String date = queryString.get("date").getFirst();
            String rid = queryString.containsKey("rid") ? queryString.get("rid").getFirst() : null;

            return Optional.of(createActivity(sid, uid, duration, date, rid));
        }


        throw new InvalidRequestException();
    }
}
