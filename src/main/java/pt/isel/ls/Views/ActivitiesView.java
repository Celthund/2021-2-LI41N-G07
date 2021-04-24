package pt.isel.ls.Views;

import pt.isel.ls.Commands.RequestHandler;
import pt.isel.ls.Commands.RequestResult;
import pt.isel.ls.DataClass.Activity;
import pt.isel.ls.Exceptions.InvalidDateException;
import pt.isel.ls.Exceptions.InvalidDurationException;
import pt.isel.ls.Exceptions.InvalidRequestException;
import pt.isel.ls.Models.ActivitiesModel;
import pt.isel.ls.Request.Method;
import pt.isel.ls.Request.Request;

import java.util.HashMap;
import java.util.LinkedList;

public class ActivitiesView implements RequestHandler {

    ActivitiesModel model = new ActivitiesModel();

    private RequestResult getActivitiesByTops(String sid, String orderBy, String date, String rid) {
        LinkedList<Activity> activities = model.getActivitiesByTops(sid, orderBy, date, rid);

        if (activities != null) {
            return new RequestResult(200, activities, String.format("Found %s activities", activities.size()));
        }
        return new RequestResult(404, null, "No activities found");
    }

    private RequestResult getActivityByUid(String uid) {
        Activity activity = model.getActivityByUid(uid);

        if (activity != null)
            return new RequestResult(200, activity, "Found user activity with id = " + activity.id);

        return new RequestResult(404, null, "Activity not found");
    }

    private RequestResult postActivity(String sid, String uid, String duration, String date, String rid) throws InvalidDateException, InvalidDurationException {
        Activity activity = model.postActivity(sid, uid, duration, date, rid);

        if (activity != null) {
            return new RequestResult(200, activity, "Activity created with success with id = " + activity.id);
        }
        return new RequestResult(500, null, "Failed to create activity.");
    }

    private RequestResult getActivityByAid_Sid(String aid, String sid) {
        Activity activity = model.getActivityByAidSid(aid, sid);
        if (activity != null) {
            return new RequestResult(200, activity, "Found activity with id = " + activity.id);
        }
        return new RequestResult(500, null, "Activity not found.");
    }

    private RequestResult getActivityBySid(String sid) {
        Activity activity = model.getActivityBySid(sid);
        if (activity != null) {
            return new RequestResult(200, activity, "Found activity with id = " + activity.id);
        }
        return new RequestResult(500, null, "Activity not found.");
    }

    @Override
    public RequestResult execute(Request request) throws InvalidRequestException, InvalidDateException, InvalidDurationException {
        HashMap<String, LinkedList<String>> queryString = request.getQueryString();
        HashMap<String, String> parameters = request.getParameters();

        if (request.getPath()[0].equals("sports")) {
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
