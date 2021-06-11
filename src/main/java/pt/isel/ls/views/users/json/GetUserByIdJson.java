package pt.isel.ls.views.users.json;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.GetUserByIdResult;
import pt.isel.ls.views.View;

import java.util.LinkedList;

import static pt.isel.ls.views.builders.json.JsonGetter.emptyDataSetJson;
import static pt.isel.ls.views.builders.json.JsonGetter.getUserJson;

public class GetUserByIdJson implements View {

    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws InvalidJsonException {
        User user = ((GetUserByIdResult) requestResult).getData();

        if (user == null) {
            return emptyDataSetJson(requestResult.getMessage(),
                requestResult.getStatus()).toString();
        }

        return getUserJson(user).toString();
    }
}
