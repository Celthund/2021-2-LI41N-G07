package pt.isel.ls.views.activities.html;

import java.util.LinkedList;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivityByAidSidResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;
import static pt.isel.ls.views.builders.html.HtmlGetter.emptyDataSetHtml;
import static pt.isel.ls.views.builders.html.HtmlGetter.getActivityHtmlList;

public class GetActivityByAidSidHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws AppException {
        Activity activity = ((GetActivityByAidSidResult) requestResult).getData();

        if (activity == null) {
            return emptyDataSetHtml(requestResult.getMessage()).toString();
        }

        LinkedList<Element> allElements = new LinkedList<>();
        allElements.add(h1("Activity: " + activity.aid));
        allElements.add(dl(getActivityHtmlList(activity).toArray(new Element[0])));
        allElements.add(br());
        allElements.add(alink("/users/" + activity.user.id, "Back to User"));
        allElements.add(alink("/sports/" + activity.sport.sid, "Back to Sport"));
        if (activity.route != null) {
            allElements.add(alink("/routes/" + activity.route.rid, "Back to Route"));
        }
        allElements
            .add(alink("/sports/" + activity.sport.sid + "/activities/?skip=0&top=5", "Back to Sports Activities"));

        return html(
            head(
                title("Activity: " + activity.aid)
            ),
            body(
                allElements.toArray(new Element[0])
            )
        ).toString();
    }
}