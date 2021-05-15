package pt.isel.ls.views.routes.plaintext;

import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.GetRouteByIdResult;
import pt.isel.ls.views.View;

public class GetRouteByIdPlainText implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) {
        Route route = ((GetRouteByIdResult) requestResult).data;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Route {rid: ")
                .append(route.rid)
                .append(", distance: ")
                .append(route.distance)
                .append(", startLocation: ")
                .append(route.startLocation)
                .append(", endLocation")
                .append(route.endLocation)
                .append("}\n");

        return stringBuilder.toString();
    }
}
