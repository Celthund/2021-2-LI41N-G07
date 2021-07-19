package pt.isel.ls.handlers.users;

import java.util.LinkedList;
import java.util.Optional;
import javax.sql.DataSource;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.mappers.ActivitiesMapper;
import pt.isel.ls.mappers.UserMapper;
import pt.isel.ls.mappers.domainclasses.Activity;
import pt.isel.ls.mappers.domainclasses.User;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetUserByIdResult;
import pt.isel.ls.databaseutils.Database;
import pt.isel.ls.databaseutils.TransactionManager;

public class GetUserByIdHandler implements RequestHandler {

    UserMapper userMapper = new UserMapper();
    ActivitiesMapper activityModel = new ActivitiesMapper();

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        if (request.getParameters().containsKey("uid")) {
            DataSource dt = Database.getDataSource();
            TransactionManager tm = new TransactionManager(dt);

            return Optional.of(tm.execute(conn -> {
                String uid = request.getParameters().get("uid");

                User user = userMapper.getUserById(uid, conn);
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
