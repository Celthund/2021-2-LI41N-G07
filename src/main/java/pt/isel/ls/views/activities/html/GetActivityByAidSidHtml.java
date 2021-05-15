package pt.isel.ls.views.activities.html;

import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivityByAidSidResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;

import java.util.LinkedList;

import static pt.isel.ls.views.builders.html.HtmlBuilder.*;
import static pt.isel.ls.views.builders.html.HtmlGetter.getActivityHTMLTableHeader;
import static pt.isel.ls.views.builders.html.HtmlGetter.getActivityHTMLTableRow;

public class GetActivityByAidSidHtml implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) throws AppException {
        Activity activity = ((GetActivityByAidSidResult) requestResult).getData();
        LinkedList<Element> elements = new LinkedList<>();

        elements.addAll(getActivityHTMLTableHeader());

        elements.add(tr(getActivityHTMLTableRow(activity).toArray(new Element[0])));

        Element html =
                html(
                        head(
                                title("Activities")
                        ),
                        body(
                                h1("Activities"),
                                table(elements.toArray(new Element[0]))
                        )
                );
        return html.toString();
    }
}