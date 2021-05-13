package pt.isel.ls.views.users.json.sports;

import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.CreateSportResult;
import pt.isel.ls.results.users.CreateUserResult;
import pt.isel.ls.views.View;

import static pt.isel.ls.views.builders.json.JsonBuilder.jsonObject;
import static pt.isel.ls.views.builders.json.JsonBuilder.jsonPut;

public class CreateSportJson implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) {
        Sport sport = ((CreateSportResult) requestResult).data;
        return jsonObject(
                jsonPut("Sport ID", sport.sid),
                jsonPut("Name", sport.name),
                jsonPut("Description", sport.description)
        ).toString();
    }
}
