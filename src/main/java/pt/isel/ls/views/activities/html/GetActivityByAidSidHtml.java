package pt.isel.ls.views.activities.html;

import java.util.LinkedList;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivityByAidSidResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;
import static pt.isel.ls.views.builders.html.HtmlGetter.*;

public class GetActivityByAidSidHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws AppException {
        Activity activity = ((GetActivityByAidSidResult) requestResult).getData();

        if (activity == null) {
            return emptyDataSetHtml(requestResult.getMessage()).toString();
        }

        LinkedList<Element> elements = new LinkedList<>(getActivityHtmlTableHeader());

        elements.add(tr(getActivityHtmlTableRow(activity).toArray(new Element[0])));

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