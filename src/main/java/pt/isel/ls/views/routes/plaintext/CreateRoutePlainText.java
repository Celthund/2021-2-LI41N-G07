package pt.isel.ls.views.routes.plaintext;

import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.CreateRouteResult;
import pt.isel.ls.views.View;

public class CreateRoutePlainText implements View {

    @Override
    public String getRepresentation(RequestResult requestResult) {
        Route route = ((CreateRouteResult) requestResult).data;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Route {rid: ")
                .append(route.rid)
                .append(", distance: ")
                .append(route.distance)
                .append(", startLocation: ")
                .append(route.startLocation)
                .append(", endLocation: ")
                .append(route.endLocation)
                .append("}\n");
        return stringBuilder.toString();
    }
}
