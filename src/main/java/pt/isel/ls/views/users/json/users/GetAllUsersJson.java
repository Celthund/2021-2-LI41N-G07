package pt.isel.ls.views.users.json.users;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetAllUsersResult;
import pt.isel.ls.views.builders.json.parts.JsonObject;

import static pt.isel.ls.views.builders.json.JsonBuilder.*;
import java.util.LinkedList;

public class GetAllUsersJson extends AbstractUserJson {
    @Override
    public String getRepresentation(RequestResult requestResult) throws InvalidJsonException {
        LinkedList<User> users = ((GetAllUsersResult) requestResult).data;
        LinkedList<JsonObject> objects = new LinkedList<>();

        for (User user : users) {
            objects.add(getUserJson(user));
        }

        return jsonObject(
                jsonPut("Users", jsonArray(objects))
        ).toString();
    }
}
