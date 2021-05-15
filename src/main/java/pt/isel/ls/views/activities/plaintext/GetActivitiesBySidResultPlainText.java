package pt.isel.ls.views.activities.plaintext;

import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivitiesBySidResult;
import pt.isel.ls.views.View;

import java.util.LinkedList;

import static pt.isel.ls.views.builders.plaintext.PlainTextGetter.getActivityPlainText;

public class GetActivitiesBySidResultPlainText implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) {
        LinkedList<Activity> activities = ((GetActivitiesBySidResult)requestResult).getData();
        StringBuilder stringBuilder = new StringBuilder();
        for (Activity activity : activities)
            stringBuilder.append(getActivityPlainText(activity)).append("\n");

        return stringBuilder.toString();
    }
}
