package pt.isel.ls.views.activities.plaintext;

import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.CreateActivityResult;
import pt.isel.ls.views.View;
import static pt.isel.ls.views.builders.plaintext.PlainTextGetter.getActivityPlainText;

public class CreateActivityPlainText implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) {
        Activity activity = ((CreateActivityResult) requestResult).getData();

        if (activity == null) {
            return requestResult.getMessage();
        }

        return getActivityPlainText(activity);
    }
}