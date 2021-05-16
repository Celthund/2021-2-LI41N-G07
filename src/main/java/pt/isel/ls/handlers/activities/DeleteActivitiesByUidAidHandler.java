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
import pt.isel.ls.results.activities.DeleteActivitiesByUidAidResult;

public class DeleteActivitiesByUidAidHandler implements RequestHandler {

    ActivitiesModel model = new ActivitiesModel();

    private DeleteActivitiesByUidAidResult deleteActivitiesByUidAid(String uid, LinkedList<String> aid)
            throws AppException {
        // LinkedList that will contain all the activities Model each one
        // containing the data with the query results
        LinkedList<Activity> activities = model.deleteActivity(uid, aid);

        // If ac
        if (activities != null) {
            return new DeleteActivitiesByUidAidResult(200, activities, "Deleted with success "
                + activities.size() + ((activities.size() == 1) ? "Activity" : "Activities"));
        }
        return new DeleteActivitiesByUidAidResult(500, null, "Failed to delete any activity.");


    }


    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        HashMap<String, LinkedList<String>> queryString = request.getQueryStrings();
        HashMap<String, String> parameters = request.getParameters();
        if (parameters.containsKey("uid")
            && queryString.containsKey("activity")) {
            String uid = parameters.get("uid");
            // List that stores all the activity the user pretends to delete
            LinkedList<String> aid = queryString.get("activity");
            // Returns the Result that contains the query from the deleted
            return Optional.of(deleteActivitiesByUidAid(uid, aid));
        }
        throw new InvalidRequestException();
    }
}
