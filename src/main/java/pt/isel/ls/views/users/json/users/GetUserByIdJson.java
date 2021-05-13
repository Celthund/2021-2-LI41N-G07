package pt.isel.ls.views.users.json.users;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetUserByIdResult;
import pt.isel.ls.views.View;

import static pt.isel.ls.views.builders.json.JsonBuilder.*;

public class GetUserByIdJson implements View {

    @Override
    public String getRepresentation(RequestResult requestResult) {
        User user = ((GetUserByIdResult)requestResult).data;
        return jsonObject(
                jsonPut("User ID", user.id),
                jsonPut("Name", user.name),
                jsonPut("Email", user.email)
        ).toString();
    }
}
