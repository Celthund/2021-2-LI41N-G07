package pt.isel.ls.views.activities.plaintext;

import java.util.LinkedList;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.DeleteActivitiesByUidAidResult;
import pt.isel.ls.views.View;
import static pt.isel.ls.views.builders.plaintext.PlainTextGetter.getActivityPlainText;

public class DeleteActivitiesByUidAidPlainText implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws AppException {
        LinkedList<Activity> activities = ((DeleteActivitiesByUidAidResult) requestResult).getData();
        StringBuilder stringBuilder = new StringBuilder();

        if (activities == null) {
            return requestResult.getMessage();
        }
        stringBuilder.append("Deleted activities\n");
        for (Activity activity : activities) {
            stringBuilder.append(getActivityPlainText(activity)).append("\n");
        }
        return stringBuilder.toString();
    }
}
