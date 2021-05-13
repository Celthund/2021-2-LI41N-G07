package pt.isel.ls.views.users.json.users;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.CreateUserResult;
import pt.isel.ls.views.View;
import static pt.isel.ls.views.builders.json.JsonBuilder.*;

public class CreateUserJson implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) throws InvalidJsonException {
        User user = ((CreateUserResult) requestResult).data;
        return jsonObject(
                jsonPut("User ID", user.id),
                jsonPut("Name", user.name),
                jsonPut("Email", user.email)
        ).toString();
    }
}
