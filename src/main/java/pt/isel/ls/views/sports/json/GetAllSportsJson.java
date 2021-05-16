package pt.isel.ls.views.sports.json;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.GetAllSportsResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.json.parts.JsonObject;
import java.util.LinkedList;
import static pt.isel.ls.views.builders.json.JsonBuilder.*;
import static pt.isel.ls.views.builders.json.JsonGetter.emptyDataSetJson;
import static pt.isel.ls.views.builders.json.JsonGetter.getSportJson;

public class GetAllSportsJson implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws InvalidJsonException {
        LinkedList<Sport> sports = ((GetAllSportsResult) requestResult).getData();
        LinkedList<JsonObject> objects = new LinkedList<>();

        if (sports == null)
            return emptyDataSetJson(requestResult.getMessage(),
                    requestResult.getStatus()).toString();

        for (Sport sport : sports) {
            objects.add(getSportJson(sport));
        }

        return jsonObject(jsonPut("Sports", jsonArray(objects))).toString();
    }
}
