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
import pt.isel.ls.utils.TransactionManager;
import pt.isel.ls.utils.DataSource;

public class CreateUserHandler implements RequestHandler {

    UserModel model = new UserModel();

    @Override
    public Optional<RequestResult<?>> execute(Request request) throws AppException {
        if (request.getQueryStrings().containsKey("name") && request.getQueryStrings().containsKey("email")) {
            javax.sql.DataSource dt = DataSource.getDataSource();
            TransactionManager tm = new TransactionManager(dt);

            return Optional.of(tm.execute(conn -> {
                String name = request.getQueryStrings().get("name").getFirst();
                String email = request.getQueryStrings().get("email").getFirst();
                User user = model.createUser(name, email, conn);
                if (user != null) {
                    return new CreateUserResult(200, user,
                        "User created with success with id = " + user.id + "");
                }
                return new CreateUserResult(500, null, "Failed to create user.");
            }));
        }
        throw new InvalidRequestException("Missing user name or email.");
    }
}
