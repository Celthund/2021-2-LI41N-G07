package pt.isel.ls.views.activities.html;

import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivitiesByTopsResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;

import java.util.LinkedList;

import static pt.isel.ls.views.builders.html.HtmlBuilder.*;
import static pt.isel.ls.views.builders.html.HtmlGetter.getActivityHTMLTableRow;

public class GetActivitiesByTopsHtml implements View {
    @Override
    public String getRepresentation(RequestResult requestResult) throws AppException {
        LinkedList<Activity> activities = ((GetActivitiesByTopsResult) requestResult).data;
        LinkedList<Element> elements = new LinkedList<>();

        elements.add(tr(
                th("Activity Id"),
                th("Date"),
                th("Duration"),
                th("User Id"),
                th("User Name"),
                th("User Email"),
                th("Sport Id"),
                th("Sport Name"),
                th("Sport Description"),
                th("Router Id"),
                th("Start Location"),
                th("End Location"),
                th("Distance")
        ));

        for (Activity activity : activities) {
            elements.add(tr(getActivityHTMLTableRow(activity).toArray(new Element[0])));
        }

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