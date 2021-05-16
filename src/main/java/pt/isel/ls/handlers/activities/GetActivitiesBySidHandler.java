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
import pt.isel.ls.results.activities.GetActivitiesBySidResult;

public class GetActivitiesBySidHandler implements RequestHandler {

    ActivitiesModel model = new ActivitiesModel();

    private GetActivitiesBySidResult getActivitiesBySid(String sid, String skip, String top) throws AppException {
        LinkedList<Activity> activities = model.getActivitiesBySid(sid, skip, top);
        if (activities != null) {
            return new GetActivitiesBySidResult(200, activities, "Found " + activities.size()
                + " activities with sid = " + sid);
        }
        return new GetActivitiesBySidResult(500, null, "Activity not found.");
    }

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        HashMap<String, String> parameters = request.getParameters();
        HashMap<String, LinkedList<String>> queryString = request.getQueryStrings();

        if (parameters.containsKey("sid")) {
            String skip = queryString.containsKey("skip") ? queryString.get("skip").getFirst() : null;
            String top = queryString.containsKey("top") ? queryString.get("top").getFirst() : null;
            return Optional.of(getActivitiesBySid(queryString.get("sid").getFirst(), skip, top));
        }

        throw new InvalidRequestException();
    }
}
