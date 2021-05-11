package pt.isel.ls.handlers.users;

import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.UserModel;
import pt.isel.ls.request.Request;
import java.util.LinkedList;
import java.util.Optional;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetAllUsersResult;

public class GetAllUsersHandler implements RequestHandler {

    UserModel model = new UserModel();

    public GetAllUsersResult getAllUsers() throws AppException {
        LinkedList<User> users = model.getAllUsers();
        if (users.size() > 0) {
            return new GetAllUsersResult(200, users, users.size() + " users found.");
        }
        return new GetAllUsersResult(404, null, "No users found.");
    }

    @Override
    public Optional<RequestResult> execute(Request request) throws AppException {
        return Optional.of(getAllUsers());
    }
}
