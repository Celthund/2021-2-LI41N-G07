package pt.isel.ls.handlers.users;

import pt.isel.ls.commands.RequestHandler;
import pt.isel.ls.commands.RequestResult;
import pt.isel.ls.Models.domainclasses.User;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.Models.UserModel;
import pt.isel.ls.Request.Request;
import java.util.LinkedList;
import java.util.Optional;

public class GetAllUsersHandler implements RequestHandler {

    UserModel model = new UserModel();

    public RequestResult getAllUsers() throws AppException {
        LinkedList<User> users = model.getAllUsers();
        if (users.size() > 0) {
            return new RequestResult(200, users, users.size() + " users found.");
        }
        return new RequestResult(404, null, "No users found.");
    }

    @Override
    public Optional<RequestResult> execute(Request request) throws AppException {
        return Optional.of(getAllUsers());
    }
}
