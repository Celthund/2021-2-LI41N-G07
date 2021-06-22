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
import pt.isel.ls.utils.TransactionManager;
import pt.isel.ls.utils.DataSource;

public class CreateActivityHandler implements RequestHandler {

    // Call the respective model, this model class will handle the query
    ActivitiesModel model = new ActivitiesModel();

    // This method will search the request and act accordingly returning and Optional
    //containing the RequestResult with all the information from the query
    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        HashMap<String, LinkedList<String>> queryString = request.getQueryStrings();
        HashMap<String, String> parameters = request.getParameters();
        if (parameters.containsKey("sid") && queryString.containsKey("uid") && queryString.containsKey("duration")
            && queryString.containsKey("date")) {

            javax.sql.DataSource dt = DataSource.getDataSource();
            TransactionManager tm = new TransactionManager(dt);

            return Optional.of(tm.execute(conn -> {
                String sid = parameters.get("sid");
                String uid = queryString.get("uid").getFirst();
                String duration = queryString.get("duration").getFirst();
                String date = queryString.get("date").getFirst();
                // Rid is optional if the dictionary doesnt contains it just sends alink null
                String rid = queryString.containsKey("rid") ? queryString.get("rid").getFirst() : null;

                // "Holder" class that holds the columns value from the row
                Activity activity = model.createActivity(sid, uid, duration, date, rid, conn);

                // If it null it means that there was alink problem creating the activity, it not it means
                //it created the activity so it send the activity holder that contains all the values
                if (activity != null) {
                    return new CreateActivityResult(200, activity,
                        "Activity created with success with id = " + activity.aid);
                }
                return new CreateActivityResult(500, null, "Failed to create activity.");

            }));
        }
        throw new InvalidRequestException("Missing sport id, user id, duration or date.");
    }
}
