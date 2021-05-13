package pt.isel.ls.views.users.json.routes;

import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.CreateRouteResult;
import pt.isel.ls.views.View;

import static pt.isel.ls.views.builders.json.JsonBuilder.jsonObject;
import static pt.isel.ls.views.builders.json.JsonBuilder.jsonPut;

public class CreateRouteJson implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) {
        Route route = ((CreateRouteResult) requestResult).data;
        return jsonObject(
                jsonPut("Sport ID", route.rid),
                jsonPut("StartLocation", route.startLocation),
                jsonPut("EndLocation", route.endLocation),
                jsonPut("Distance", route.distance)
        ).toString();
    }
}
