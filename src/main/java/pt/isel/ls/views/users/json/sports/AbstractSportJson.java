package pt.isel.ls.views.users.json.sports;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.json.parts.JsonObject;

import static pt.isel.ls.views.builders.json.JsonBuilder.jsonObject;
import static pt.isel.ls.views.builders.json.JsonBuilder.jsonPut;

public abstract class AbstractSportJson implements View {
    protected JsonObject getSportJson(Sport sport) throws InvalidJsonException {
        return jsonObject(
                jsonPut("Sport ID", sport.sid),
                jsonPut("Name", sport.name),
                jsonPut("Description", sport.description)
        );
    }
}
