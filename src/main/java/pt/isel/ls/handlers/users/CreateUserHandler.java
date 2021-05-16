package pt.isel.ls.handlers.users;

import java.util.Optional;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.exceptions.InvalidRequestException;
import pt.isel.ls.models.UserModel;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.CreateUserResult;

public class CreateUserHandler implements RequestHandler {

    UserModel model = new UserModel();

    public CreateUserResult createUser(String name, String email) throws AppException {
        User user = model.createUser(name, email);
        if (user != null) {
            return new CreateUserResult(200, user, "User created with success with id = " + user.id + "");
        }
        return new CreateUserResult(500, null, "Failed to create user.");
    }

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        if (request.getQueryStrings().containsKey("name")
            && request.getQueryStrings().containsKey("email")) {
            return Optional.of(createUser(
                request.getQueryStrings().get("name").getFirst(),
                request.getQueryStrings().get("email").getFirst()));
        }
        throw new InvalidRequestException();
    }
}
