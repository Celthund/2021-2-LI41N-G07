package pt.isel.ls.Views;

import pt.isel.ls.Commands.RequestHandler;
import pt.isel.ls.Commands.RequestResult;
import pt.isel.ls.DataClass.Activity;
import pt.isel.ls.Exceptions.InvalidRequestException;
import pt.isel.ls.Models.ActivitiesModel;
import pt.isel.ls.Request.Method;
import pt.isel.ls.Request.Request;

import java.util.LinkedList;

public class ActivitiesView implements RequestHandler {

    ActivitiesModel model = new ActivitiesModel();

    public RequestResult getActivityById(String id) {
        Activity activity = model.getActivityById(id);
        if (activity != null) {
            return new RequestResult(
                    200,
                    activity,
                    "Found activity with id = " + id);
        }
        return new RequestResult(404, null, "Activity not found.");
    }

    public RequestResult getAllActivities() {
        LinkedList<Activity> activities = model.getAllActivities();
        if (activities.size() > 0) {
            return new RequestResult(200, activities, activities.size() + " activities found.");
        }
        return new RequestResult(404, null, "No activities found.");
    }

    public RequestResult createActivity(String uid, String rid, String date, String duration) {
        Activity activity = model.createActivity(uid, rid, date, duration);
        if (activity != null) {
            return new RequestResult(200, activity, "Activity created with success with id = " + activity.id + "");
        }
        return new RequestResult(500, null, "Failed to create activity.");
    }

    public RequestResult createActivity(String uid, String date, String duration) {
        Activity activity = model.createActivity(uid, date, duration);
        if (activity != null) {
            return new RequestResult(200, activity, "Activity created with success with id = " + activity.id + "");
        }
        return new RequestResult(500, null, "Failed to create activity.");
    }

    @Override
    public RequestResult execute(Request request) throws InvalidRequestException {
        if (request.getMethod() == Method.GET && request.getParameters().size() == 0)
            return getAllActivities();

        if (request.getMethod() == Method.GET && request.getParameters().containsKey("aid"))
            return getActivityById(request.getParameters().get("aid"));

        if (request.getMethod() == Method.POST &&
                request.getQueryString().containsKey("uid") &&
                request.getQueryString().containsKey("date") &&
                request.getQueryString().containsKey("duration")
        ) {
            if (request.getQueryString().containsKey("rid"))
                return createActivity(
                        request.getQueryString().get("uid").getFirst(),
                        request.getQueryString().get("rid").getFirst(),
                        request.getQueryString().get("date").getFirst(),
                        request.getQueryString().get("duration").getFirst());
            return createActivity(
                    request.getQueryString().get("uid").getFirst(),
                    request.getQueryString().get("date").getFirst(),
                    request.getQueryString().get("duration").getFirst());
        }

        throw new InvalidRequestException();
    }
}
