package pt.isel.ls.views.users.json.routes;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.json.parts.JsonObject;

import static pt.isel.ls.views.builders.json.JsonBuilder.*;

public abstract class AbstractRouteJson implements View {
    protected JsonObject getRouteJson(Route route) throws InvalidJsonException {
        return jsonObject(
                jsonPut("Sport ID", route.rid),
                jsonPut("StartLocation", route.startLocation),
                jsonPut("EndLocation", route.endLocation),
                jsonPut("Distance", route.distance)
        );
    }
}
