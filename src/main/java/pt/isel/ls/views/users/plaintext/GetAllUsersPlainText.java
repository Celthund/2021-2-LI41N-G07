package pt.isel.ls.views.users.plaintext;

import java.util.LinkedList;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetAllUsersResult;
import pt.isel.ls.views.View;

public class GetAllUsersPlainText implements View {

    @Override
    public String getRepresentation(RequestResult requestResult) {
        LinkedList<User> users = ((GetAllUsersResult) requestResult).data;
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : users) {
            stringBuilder.append("User {id: ").append(
                user.id).append(" , name: ").append(user.name).append(" , email: ").append(user.email).append("}\n");
        }
        return stringBuilder.toString();
    }

}
