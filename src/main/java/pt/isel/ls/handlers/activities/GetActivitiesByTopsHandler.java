package pt.isel.ls.handlers.activities;

import pt.isel.ls.commands.RequestHandler;
import pt.isel.ls.commands.RequestResult;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.models.ActivitiesModel;
import pt.isel.ls.request.Request;
import java.util.LinkedList;
import java.util.Optional;

public class GetActivitiesByTopsHandler implements RequestHandler {

    ActivitiesModel model = new ActivitiesModel();

    private RequestResult getActivitiesByTops(String sid, String orderBy, String date, String rid)
            throws AppException {
        LinkedList<Activity> activities = model.getActivitiesByTops(sid, orderBy, date, rid);

        if (activities != null) {
            return new RequestResult(200, activities, String.format("Found %s activities", activities.size()));
        }
        return new RequestResult(404, null, "No activities found");
    }


    @Override
    public Optional<RequestResult> execute(Request request) throws AppException {
        if (request.getQueryStrings().containsKey("sid") && request.getQueryStrings().containsKey("orderBy")) {
            String sid = request.getQueryStrings().get("sid").getFirst();
            String orderBy = request.getQueryStrings().get("orderBy").getFirst();
            String date = request.getQueryStrings().containsKey("date")
                    ? request.getQueryStrings().get("date").getFirst() : null;
            String rid = request.getQueryStrings().containsKey("rid")
                    ? request.getQueryStrings().get("rid").getFirst() : null;
            return Optional.of(getActivitiesByTops(sid, orderBy, date, rid));
        }
        throw new InvalidRequestException();
    }
}
