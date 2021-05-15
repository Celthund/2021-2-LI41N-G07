package pt.isel.ls.views.users.plaintext;

import java.util.LinkedList;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetAllUsersResult;
import pt.isel.ls.views.View;

import static pt.isel.ls.views.builders.plaintext.PlainTextGetter.getUserPlainText;

public class GetAllUsersPlainText implements View {

    @Override
    public String getRepresentation(RequestResult requestResult) {
        LinkedList<User> users = ((GetAllUsersResult) requestResult).data;
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : users) {
            stringBuilder.append(getUserPlainText(user));
        }
        return stringBuilder.toString();
    }

}
