package pt.isel.ls.views.users.json.users;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetAllUsersResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.json.JsonElement;
import pt.isel.ls.views.builders.json.parts.JsonObject;

import static pt.isel.ls.views.builders.json.JsonBuilder.*;
import java.util.LinkedList;

public class GetAllUsersJson implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) throws InvalidJsonException {
        LinkedList<User> users = ((GetAllUsersResult) requestResult).data;
        LinkedList<JsonObject> objects = new LinkedList<>();

        for (User user : users) {
            objects.add(jsonObject(
                    jsonPut("User ID", user.id),
                    jsonPut("Name", user.name),
                    jsonPut("Email", user.email)
            ));
        }

        return jsonObject(
                jsonPut("Users", jsonArray(objects))
        ).toString();
    }
}
