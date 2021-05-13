package pt.isel.ls.views.users.json.users;
import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetUserByIdResult;

public class GetUserByIdJson extends AbstractUserJson {

    @Override
    public String getRepresentation(RequestResult requestResult) throws InvalidJsonException {
        User user = ((GetUserByIdResult)requestResult).data;
        return getUserJson(user).toString();
    }
}
