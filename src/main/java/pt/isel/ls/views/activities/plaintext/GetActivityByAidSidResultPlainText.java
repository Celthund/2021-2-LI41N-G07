package pt.isel.ls.views.activities.plaintext;

import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivityByAidSidResult;
import pt.isel.ls.views.View;
import static pt.isel.ls.views.builders.plaintext.PlainTextGetter.getActivityPlainText;

public class GetActivityByAidSidResultPlainText implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) {
        Activity activity = ((GetActivityByAidSidResult)requestResult).getData();

        return getActivityPlainText(activity);
    }
}
