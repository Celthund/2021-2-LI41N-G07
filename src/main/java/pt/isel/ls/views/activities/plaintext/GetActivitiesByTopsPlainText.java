package pt.isel.ls.views.activities.plaintext;

import java.util.LinkedList;
import pt.isel.ls.mappers.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivitiesByTopsResult;
import pt.isel.ls.views.View;
import static pt.isel.ls.views.builders.plaintext.PlainTextGetter.getActivityPlainText;

public class GetActivitiesByTopsPlainText implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) {
        LinkedList<Activity> activities = ((GetActivitiesByTopsResult) requestResult).getData();
        StringBuilder stringBuilder = new StringBuilder();

        if (activities == null) {
            return requestResult.getMessage();
        }

        for (Activity activity : activities) {
            stringBuilder.append(getActivityPlainText(activity)).append("\n");
        }
        return stringBuilder.toString();
    }
}
