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

    public GetAllUsersResult getAllUsers(String skip, String top) throws AppException {
        LinkedList<User> users = model.getAllUsers(skip, top);
        if (users.size() > 0) {
            return new GetAllUsersResult(200, users, users.size() + " users found.");
        }
        return new GetAllUsersResult(404, null, "No users found.");
    }

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {

        if(request.getQueryStrings().containsKey("skip") && request.getQueryStrings().containsKey("top") )
            return  Optional.of(getAllUsers(request.getQueryStrings().get("skip").getFirst(), request.getQueryStrings().get("top").getFirst()));

        return  Optional.of(getAllUsers(null, null));
    }
}
