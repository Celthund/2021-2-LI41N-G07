package pt.isel.ls.Views;

import pt.isel.ls.Commands.RequestHandler;
import pt.isel.ls.Commands.RequestResult;
import pt.isel.ls.DataClass.Activity;
import pt.isel.ls.Exceptions.BadRequestException;
import pt.isel.ls.Exceptions.InvalidRequestException;
import pt.isel.ls.Exceptions.ServerErrorException;
import pt.isel.ls.Models.ActivitiesModel;
import pt.isel.ls.Request.Method;
import pt.isel.ls.Request.Request;

import java.util.HashMap;
import java.util.LinkedList;

public class ActivitiesView implements RequestHandler {

    ActivitiesModel model = new ActivitiesModel();

    private RequestResult getActivitiesByTops(String sid, String orderBy, String date, String rid) throws BadRequestException, ServerErrorException {
        // List to store all the activities
        LinkedList<Activity> activities = model.getActivitiesByTops(sid, orderBy, date, rid);

        // Return a message with an HTML status code
        if (activities != null) {
            return new RequestResult(200, activities, String.format("Found %s activities", activities.size()));
        }
        return new RequestResult(404, null, "No activities found");
    }

    private RequestResult getActivityByUid(String uid) throws ServerErrorException {
        // Stores the values got from the getActivityByUid() in the ActivityModel class
        Activity activity = model.getActivityByUid(uid);

        // Return a message with an HTML status code
        if (activity != null)
            return new RequestResult(200, activity, "Found user activity with id = " + activity.id);

        return new RequestResult(404, null, "Activity not found");
    }

    private RequestResult postActivity(String sid, String uid, String duration, String date, String rid) throws BadRequestException, ServerErrorException {
        // Stores the values got from the postActivity() in the ActivityModel class
        Activity activity = model.postActivity(sid, uid, duration, date, rid);

        // Return a message with an HTML status code
        if (activity != null) {
            return new RequestResult(200, activity, "Activity created with success with id = " + activity.id);
        }
        return new RequestResult(500, null, "Failed to create activity.");
    }

    private RequestResult getActivityByAid_Sid(String aid, String sid) throws ServerErrorException {
        // Stores the values got from the getActivityByAidSid() in the ActivityModel class
        Activity activity = model.getActivityByAidSid(aid, sid);
        if (activity != null) {
            return new RequestResult(200, activity, "Found activity with id = " + activity.id);
        }
        return new RequestResult(500, null, "Activity not found.");
    }

    private RequestResult getActivityBySid(String sid) throws ServerErrorException {
        // Stores the values got from the getActivityBySid() in the ActivityModel class
        Activity activity = model.getActivityBySid(sid);

        // Return a message with an HTML status code
        if (activity != null) {
            return new RequestResult(200, activity, "Found activity with id = " + activity.id);
        }
        return new RequestResult(500, null, "Activity not found.");
    }

    // The execute method receives a request, checks the arguments in it so it can get the query correspondent to the Method, Path and/or parameters in the Request class
    @Override
    public RequestResult execute(Request request) throws InvalidRequestException, BadRequestException, ServerErrorException {
        // Holder for the queryString and the parameters got form the request sent by the user
        HashMap<String, LinkedList<String>> queryString = request.getQueryStrings();
        HashMap<String, String> parameters = request.getParameters();

        // The getPath() method in Request class returns the first position stores from the path array which has stores the path separated by "/"
        if (request.getPath()[0].equals("sports")) {
            // If the method was GET and the path sent was
            if (request.getMethod() == Method.GET && parameters.containsKey("aid") && parameters.containsKey("sid"))
                return getActivityByAid_Sid(parameters.get("aid"), parameters.get("sid"));

            if (request.getMethod() == Method.GET && parameters.containsKey("sid"))
                return getActivityBySid(parameters.get("sid"));

            if (request.getMethod() == Method.POST && parameters.containsKey("sid")
                    && queryString.containsKey("uid")
                    && queryString.containsKey("duration")
                    && queryString.containsKey("date")) {

                String sid = parameters.get("sid");
                String uid = queryString.get("uid").getFirst();
                String duration = queryString.get("duration").getFirst();
                String date = queryString.get("date").getFirst();
                String rid = queryString.containsKey("rid") ? queryString.get("rid").getFirst() : null;

                return postActivity(sid, uid, duration, date, rid);
            }

        } else if (request.getPath()[0].equals("users") && parameters.containsKey("uid")) {
            return getActivityByUid(parameters.get("uid"));

        } else if (request.getPath()[0].equals("tops")
                && queryString.containsKey("sid")
                && queryString.containsKey("orderBy")) {

            String sid = queryString.get("sid").getFirst();
            String orderBy = queryString.get("orderBy").getFirst();
            String date = queryString.containsKey("date") ? queryString.get("date").getFirst() : null;
            String rid = queryString.containsKey("rid") ? queryString.get("rid").getFirst() : null;
            return getActivitiesByTops(sid, orderBy, date, rid);
        }

        throw new InvalidRequestException();
    }
}
