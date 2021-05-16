package pt.isel.ls.views.activities.html;

import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivitiesByTopsResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;
import java.util.LinkedList;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;
import static pt.isel.ls.views.builders.html.HtmlGetter.*;

public class GetActivitiesByTopsHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws AppException {
        LinkedList<Activity> activities = ((GetActivitiesByTopsResult) requestResult).getData();

        if (activities == null)
            activities = new LinkedList<>();

        LinkedList<Element> elements = new LinkedList<>(getActivityHtmlTableHeader());

        for (Activity activity : activities) {
            elements.add(tr(getActivityHtmlTableRow(activity).toArray(new Element[0])));
        }

        return html(
                head(
                        title("Activities")
                ),
                body(
                        h1("Activities"),
                        table(elements.toArray(new Element[0]))
                )
        ).toString();
    }
}