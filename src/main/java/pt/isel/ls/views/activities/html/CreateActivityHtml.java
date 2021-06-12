package pt.isel.ls.views.activities.html;

import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.CreateActivityResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;
import static pt.isel.ls.views.builders.html.HtmlGetter.emptyDataSetHtml;
import static pt.isel.ls.views.builders.html.HtmlGetter.getActivityHtmlList;

public class CreateActivityHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws AppException {
        Activity activity = ((CreateActivityResult) requestResult).getData();

        if (activity == null) {
            return emptyDataSetHtml(requestResult.getMessage()).toString();
        }

        return html(
            head(
                title("Activity: " + activity.aid)
            ),
            body(
                h1("Activity: " + activity.aid),
                dl(getActivityHtmlList(activity).toArray(new Element[0]))
            )
        ).toString();
    }
}
