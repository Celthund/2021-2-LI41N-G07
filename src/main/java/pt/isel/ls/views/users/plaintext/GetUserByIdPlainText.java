package pt.isel.ls.views.users.plaintext;

import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetUserByIdResult;
import pt.isel.ls.views.View;

import java.util.LinkedList;

import static pt.isel.ls.views.builders.plaintext.PlainTextGetter.getUserPlainText;

public class GetUserByIdPlainText implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) {
        LinkedList<Activity> user = ((GetUserByIdResult) requestResult).getData();

        if (user == null) {
            return requestResult.getMessage();
        }
        return getUserPlainText(user.getFirst().user);
    }
}
