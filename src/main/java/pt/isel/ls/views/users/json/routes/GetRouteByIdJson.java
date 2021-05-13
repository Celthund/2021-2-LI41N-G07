package pt.isel.ls.views.users.json.routes;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.GetRouteByIdResult;
import pt.isel.ls.views.View;

import static pt.isel.ls.views.builders.json.JsonBuilder.jsonObject;
import static pt.isel.ls.views.builders.json.JsonBuilder.jsonPut;

public class GetRouteByIdJson implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) throws InvalidJsonException {
        Route route = ((GetRouteByIdResult) requestResult).data;
        return jsonObject(
                jsonPut("Sport ID", route.rid),
                jsonPut("StartLocation", route.startLocation),
                jsonPut("EndLocation", route.endLocation),
                jsonPut("Distance", route.distance)
        ).toString();
    }
}
