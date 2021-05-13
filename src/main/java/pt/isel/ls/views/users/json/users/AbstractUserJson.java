package pt.isel.ls.views.users.json.users;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.json.parts.JsonObject;

import static pt.isel.ls.views.builders.json.JsonBuilder.jsonObject;
import static pt.isel.ls.views.builders.json.JsonBuilder.jsonPut;

public abstract class AbstractUserJson implements View {

    protected JsonObject getUserJson(User user) throws InvalidJsonException {
        return jsonObject(
                jsonPut("User ID", user.id),
                jsonPut("Name", user.name),
                jsonPut("Email", user.email)
        );
    }
}
