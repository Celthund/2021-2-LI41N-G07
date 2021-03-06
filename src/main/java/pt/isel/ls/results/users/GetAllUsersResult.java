package pt.isel.ls.results.users;

import java.util.LinkedList;
import pt.isel.ls.mappers.domainclasses.User;
import pt.isel.ls.results.RequestResult;

public class GetAllUsersResult extends RequestResult<LinkedList<User>> {

    public GetAllUsersResult(int status, LinkedList<User> data, String message) {
        super(status, data, message);
    }
}
