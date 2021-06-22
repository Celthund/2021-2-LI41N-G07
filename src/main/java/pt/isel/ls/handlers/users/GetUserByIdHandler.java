package pt.isel.ls.handlers.users;

import java.util.LinkedList;
import java.util.Optional;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.models.ActivitiesModel;
import pt.isel.ls.models.UserModel;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetUserByIdResult;
import pt.isel.ls.utils.TransactionManager;
import pt.isel.ls.utils.DataSource;

public class GetUserByIdHandler implements RequestHandler {

    UserModel userModel = new UserModel();
    ActivitiesModel activityModel = new ActivitiesModel();


    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        if (request.getParameters().containsKey("uid")) {
            javax.sql.DataSource dt = DataSource.getDataSource();
            TransactionManager tm = new TransactionManager(dt);

            return Optional.of(tm.execute(conn -> {
                String uid = request.getParameters().get("uid");

                User user = userModel.getUserById(uid, conn);
                if (user != null) {
                    LinkedList<Activity> activities = activityModel.getActivitiesByUid(uid, null, null, conn);
                    // Stores all the activities of that user
                    user.setActivities(activities);
                    return new GetUserByIdResult(
                        200,
                        user,
                        "Found user with id = " + uid);
                }
                return new GetUserByIdResult(404, null, "User not found.");
            }));
        }
        throw new InvalidRequestException("Missing user id.");
    }
}
