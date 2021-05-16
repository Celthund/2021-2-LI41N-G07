package pt.isel.ls.views.users.json;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetAllUsersResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.json.parts.JsonObject;
import java.util.LinkedList;
import static pt.isel.ls.views.builders.json.JsonBuilder.*;
import static pt.isel.ls.views.builders.json.JsonGetter.*;


public class GetAllUsersJson implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws InvalidJsonException {
        LinkedList<User> users = ((GetAllUsersResult) requestResult).getData();
        LinkedList<JsonObject> objects = new LinkedList<>();

        if (users == null)
            return emptyDataSetJson(requestResult.getMessage(),
                    requestResult.getStatus()).toString();

        for (User user : users) {
            objects.add(getUserJson(user));
        }

        return jsonObject(
                jsonPut("Users", jsonArray(objects))
        ).toString();
    }
}
