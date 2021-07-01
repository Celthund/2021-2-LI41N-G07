package pt.isel.ls.views.activities.html;

import java.util.LinkedList;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.mappers.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.DeleteActivitiesByUidAidResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;
import static pt.isel.ls.views.builders.html.HtmlGetter.getActivityHtmlTableHeader;
import static pt.isel.ls.views.builders.html.HtmlGetter.getActivityHtmlTableRow;

public class DeleteActivitiesByUidAidHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws AppException {
        LinkedList<Activity> activities = ((DeleteActivitiesByUidAidResult) requestResult).getData();

        if (activities == null) {
            activities = new LinkedList<>();
        }

        LinkedList<Element> elements = new LinkedList<>(getActivityHtmlTableHeader());

        for (Activity activity : activities) {
            elements.add(tr(getActivityHtmlTableRow(activity).toArray(new Element[0])));
        }

        return html(
            head(
                title("Deleted Activities")
            ),
            body(
                h1("Deleted Activities"),
                table(elements.toArray(new Element[0]))
            )
        ).toString();
    }
}
