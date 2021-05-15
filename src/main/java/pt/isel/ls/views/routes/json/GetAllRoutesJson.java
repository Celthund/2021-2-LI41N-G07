package pt.isel.ls.views.routes.json;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.GetAllRoutesResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.json.parts.JsonObject;
import java.util.LinkedList;
import static pt.isel.ls.views.builders.json.JsonGetter.*;
import static pt.isel.ls.views.builders.json.JsonBuilder.*;

public class GetAllRoutesJson implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) throws InvalidJsonException {
        LinkedList<Route> routes = ((GetAllRoutesResult) requestResult).getData();
        LinkedList<JsonObject> objects = new LinkedList<>();

        for (Route route : routes) {
            objects.add(getRouteJson(route));
        }

        return jsonObject(
                jsonPut("Routes", jsonArray(objects))
        ).toString();
    }
}
