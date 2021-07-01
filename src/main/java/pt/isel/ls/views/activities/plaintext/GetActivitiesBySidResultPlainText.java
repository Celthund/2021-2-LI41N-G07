package pt.isel.ls.views.activities.plaintext;

import java.util.LinkedList;
import pt.isel.ls.mappers.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivitiesBySidResult;
import pt.isel.ls.views.View;
import static pt.isel.ls.views.builders.plaintext.PlainTextGetter.getActivityPlainText;

public class GetActivitiesBySidResultPlainText implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) {
        LinkedList<Activity> activities = ((GetActivitiesBySidResult) requestResult).getData();

        if (activities == null) {
            return requestResult.getMessage();
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Activity activity : activities) {
            stringBuilder.append(getActivityPlainText(activity)).append("\n");
        }

        return stringBuilder.toString();
    }
}
