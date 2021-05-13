package pt.isel.ls.views.users.json.routes;

import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.GetAllRoutesResult;
import pt.isel.ls.results.sports.GetAllSportsResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.json.parts.JsonObject;

import java.util.LinkedList;

import static pt.isel.ls.views.builders.json.JsonBuilder.*;

public class GetAllRoutesJson implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) {
        LinkedList<Route> routes = ((GetAllRoutesResult) requestResult).data;
        LinkedList<JsonObject> objects = new LinkedList<>();

        for (Route route : routes) {
            objects.add(jsonObject(
                    jsonPut("Sport ID", route.rid),
                    jsonPut("StartLocation", route.startLocation),
                    jsonPut("EndLocation", route.endLocation),
                    jsonPut("Distance", route.distance)
            ));
        }

        return jsonObject(
                jsonPut("Routes", jsonArray(objects))
        ).toString();
    }
}
