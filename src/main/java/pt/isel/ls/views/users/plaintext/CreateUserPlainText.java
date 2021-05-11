package pt.isel.ls.views.users.plaintext;

import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.CreateUserResult;
import pt.isel.ls.views.View;

public class CreateUserPlainText implements View {

    @Override
    public String getRepresentation(RequestResult requestResult) {
        User user = ((CreateUserResult) requestResult).data;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("User {id: ").append(
            user.id).append(" , name: ").append(user.name).append(" , email: ").append(user.email).append("}\n");
        return stringBuilder.toString();
    }
}
