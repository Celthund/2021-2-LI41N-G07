package pt.isel.ls.views.routes.plaintext;

import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.CreateRouteResult;
import pt.isel.ls.results.users.CreateUserResult;
import pt.isel.ls.views.View;

public class CreateRoutePlainText {

        @Override
        public String getRepresentation(RequestResult requestResult) {
            Route route = ((CreateRouteResult) requestResult).data;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Route {id: ").append(
                    user.id).append(" , name: ").append(user.name).append(" , email: ").append(user.email).append("}\n");
            return stringBuilder.toString();
}
