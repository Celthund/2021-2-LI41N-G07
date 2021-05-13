package pt.isel.ls.views.users.json.sports;

import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.GetAllSportsResult;
import pt.isel.ls.results.users.GetAllUsersResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.json.parts.JsonObject;

import java.util.LinkedList;

import static pt.isel.ls.views.builders.json.JsonBuilder.*;

public class GetAllSportsJson implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) {
        LinkedList<Sport> sports = ((GetAllSportsResult) requestResult).data;
        LinkedList<JsonObject> objects = new LinkedList<>();

        for (Sport sport : sports) {
            objects.add(jsonObject(
                    jsonPut("Sport ID", sport.sid),
                    jsonPut("Name", sport.name),
                    jsonPut("Description", sport.description)
            ));
        }

        return jsonObject(
                jsonPut("Sports", jsonArray(objects))
        ).toString();
    }
}
