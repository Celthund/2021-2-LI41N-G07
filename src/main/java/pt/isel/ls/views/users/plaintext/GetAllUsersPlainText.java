package pt.isel.ls.views.users.plaintext;

import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetAllUsersResult;
import pt.isel.ls.views.View;
import java.util.LinkedList;
import static pt.isel.ls.views.builders.plaintext.PlainTextGetter.getUserPlainText;

public class GetAllUsersPlainText implements View {

    @Override
    public String getRepresentation(RequestResult<?> requestResult) {
        LinkedList<User> users = ((GetAllUsersResult) requestResult).getData();

        if (users == null)
            return requestResult.getMessage();

        StringBuilder stringBuilder = new StringBuilder();
        for (User user : users) {
            stringBuilder.append(getUserPlainText(user));
        }
        return stringBuilder.toString();
    }

}
