package pt.isel.ls.results.users;

import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;

public class GetUserByIdResult extends RequestResult<User> {

    public GetUserByIdResult(int status, User data, String message) {
        super(status, data, message);
    }
}
