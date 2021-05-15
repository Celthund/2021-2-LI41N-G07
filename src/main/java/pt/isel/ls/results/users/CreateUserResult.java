package pt.isel.ls.results.users;

import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;

public class CreateUserResult extends RequestResult<User> {

    public CreateUserResult(int status, User data, String message) {
        super(status, data, message);
    }
}
