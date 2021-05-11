package pt.isel.ls.results.users;

import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;

public class CreateUserResult implements RequestResult {

    public final int status;
    public final User data;
    public final String message;

    public CreateUserResult(int status, User data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
