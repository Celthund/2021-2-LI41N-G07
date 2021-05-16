package pt.isel.ls.views.routes.plaintext;

import java.util.LinkedList;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.GetAllRoutesResult;
import pt.isel.ls.views.View;
import static pt.isel.ls.views.builders.plaintext.PlainTextGetter.getRoutePlainText;

public class GetAllRoutesPlainText implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) {
        LinkedList<Route> routers = ((GetAllRoutesResult) requestResult).getData();

        if (routers == null) {
            return requestResult.getMessage();
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Route route : routers) {
            stringBuilder.append(getRoutePlainText(route));
        }
        return stringBuilder.toString();
    }
}
