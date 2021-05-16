package pt.isel.ls.views.users.plaintext;

import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.CreateUserResult;
import pt.isel.ls.views.View;
import static pt.isel.ls.views.builders.plaintext.PlainTextGetter.getUserPlainText;

public class CreateUserPlainText implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) {
        User user = ((CreateUserResult) requestResult).getData();

        if (user == null) {
            return requestResult.getMessage();
        }
        return getUserPlainText(user);
    }
}
