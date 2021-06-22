package pt.isel.ls.handlers.users;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import javax.sql.DataSource;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.UserModel;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetAllUsersResult;
import pt.isel.ls.utils.Database;
import pt.isel.ls.utils.TransactionManager;

public class GetAllUsersHandler implements RequestHandler {

    UserModel model = new UserModel();

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        DataSource dt = Database.getDataSource();
        TransactionManager tm = new TransactionManager(dt);

        return Optional.of(tm.execute(conn -> {
            HashMap<String, LinkedList<String>> queryStrings = request.getQueryStrings();
            String skip = queryStrings.containsKey("skip") ? request.getQueryStrings().get("skip").getFirst() : null;
            String top = queryStrings.containsKey("top") ? request.getQueryStrings().get("top").getFirst() : null;

            LinkedList<User> users = model.getAllUsers(skip, top, conn);
            if (users.size() > 0) {
                return new GetAllUsersResult(200, users, users.size() + " users found.");
            }
            return new GetAllUsersResult(404, null, "No users found.");
        }));
    }
}
