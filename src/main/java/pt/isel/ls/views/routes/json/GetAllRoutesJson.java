package pt.isel.ls.views.routes.json;

import java.util.LinkedList;
import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.mappers.domainclasses.Route;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.GetAllRoutesResult;
import pt.isel.ls.views.View;
import static pt.isel.ls.views.builders.json.JsonBuilder.*;
import static pt.isel.ls.views.builders.json.JsonGetter.emptyDataSetJson;
import static pt.isel.ls.views.builders.json.JsonGetter.getRouteJson;
import pt.isel.ls.views.builders.json.parts.JsonObject;

public class GetAllRoutesJson implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws InvalidJsonException {
        LinkedList<Route> routes = ((GetAllRoutesResult) requestResult).getData();
        LinkedList<JsonObject> objects = new LinkedList<>();

        if (routes == null) {
            return emptyDataSetJson(requestResult.getMessage(),
                requestResult.getStatus()).toString();
        }

        for (Route route : routes) {
            objects.add(getRouteJson(route));
        }

        return jsonObject(
            jsonPut("Routes", jsonArray(objects))
        ).toString();
    }
}
