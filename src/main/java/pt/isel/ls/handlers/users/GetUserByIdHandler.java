package pt.isel.ls.handlers.users;

import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.models.UserModel;
import pt.isel.ls.request.Request;
import java.util.Optional;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetUserByIdResult;

public class GetUserByIdHandler implements RequestHandler {

    UserModel model = new UserModel();

    public GetUserByIdResult getUserById(String id) throws AppException {
        User user = model.getUserById(id);
        if (user != null) {
            return new GetUserByIdResult(
                200,
                user,
                "Found user with id = " + id);
        }
        return new GetUserByIdResult(404, null, "User not found.");
    }

    @Override
    public Optional<RequestResult> execute(Request request) throws AppException {
        if (request.getParameters().containsKey("uid")) {
            return Optional.of(getUserById(request.getParameters().get("uid")));
        }
        throw new InvalidRequestException();
    }
}
