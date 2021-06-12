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
import pt.isel.ls.results.activities.CreateActivityResult;

public class CreateActivityHandler implements RequestHandler {

    // Call the respective model, this model class will handle the query
    ActivitiesModel model = new ActivitiesModel();

    private CreateActivityResult createActivity(String sid, String uid, String duration, String date, String rid)
            throws AppException {
        // "Holder" class that holds the columns value from the row
        Activity activity = model.createActivity(sid, uid, duration, date, rid);

        // If it null it means that there was alink problem creating the activity, it not it means
        //it created the activity so it send the activity holder that contains all the values
        if (activity != null) {
            return new CreateActivityResult(200, activity, "Activity created with success with id = " + activity.aid);
        }
        return new CreateActivityResult(500, null, "Failed to create activity.");
    }

    // This method will search the request and act accordingly returning and Optional
    //contaning the RequestResult with all the information from the query
    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
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
            // Rid is optional if the dictionary doesnt contains it just sends alink null
            String rid = queryString.containsKey("rid") ? queryString.get("rid").getFirst() : null;

            // Returns the Optional with the request result containing all the information obtained from
            //the above method
            return Optional.of(createActivity(sid, uid, duration, date, rid));
        }

        throw new InvalidRequestException();
    }
}
