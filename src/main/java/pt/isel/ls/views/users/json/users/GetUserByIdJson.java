package pt.isel.ls.views.users.json.users;
import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetUserByIdResult;
import pt.isel.ls.views.View;

import static pt.isel.ls.views.users.json.JsonGetter.*;

public class GetUserByIdJson implements View {

    @Override
    public String getRepresentation(RequestResult requestResult) throws InvalidJsonException {
        User user = ((GetUserByIdResult)requestResult).data;
        return getUserJson(user).toString();
    }
}
