package pt.isel.ls.views.users.json.users;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.CreateUserResult;

public class CreateUserJson extends AbstractUserJson {
    @Override
    public String getRepresentation(RequestResult requestResult) throws InvalidJsonException {
        User user = ((CreateUserResult) requestResult).data;
        return getUserJson(user).toString();
    }
}
