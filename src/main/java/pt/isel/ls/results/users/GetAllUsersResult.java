package pt.isel.ls.results.users;

import java.util.LinkedList;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;

public class GetAllUsersResult implements RequestResult {

    public final int status;
    public final LinkedList<User> data;
    public final String message;

    public GetAllUsersResult(int status, LinkedList<User> data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
