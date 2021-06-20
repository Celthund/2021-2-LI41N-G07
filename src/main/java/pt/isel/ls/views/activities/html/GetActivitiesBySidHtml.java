package pt.isel.ls.views.activities.html;

import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivitiesBySidResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;

import java.util.HashMap;
import java.util.LinkedList;

import static pt.isel.ls.views.PageNavigation.getSkip;
import static pt.isel.ls.views.PageNavigation.getTop;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;
import static pt.isel.ls.views.builders.html.HtmlGetter.getActivityHtmlTableHeader;
import static pt.isel.ls.views.builders.html.HtmlGetter.getActivityHtmlTableRow;

public class GetActivitiesBySidHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws AppException {
        LinkedList<Activity> activities = ((GetActivitiesBySidResult) requestResult).getData();
        String sportId = requestResult.getRequest().getParameters().get("sid");

        if (activities == null) {
            activities = new LinkedList<>();
        }

        LinkedList<Element> elements = new LinkedList<>(getActivityHtmlTableHeader());
        HashMap<String, LinkedList<String>> queryString = requestResult.getRequest().getQueryStrings();
        LinkedList<Element> allElements = getFooter(queryString, activities, sportId);

        if (activities.size() > 0) {
            for (Activity activity : activities) {
                elements.add(tr(getActivityHtmlTableRow(activity).toArray(new Element[0])));
            }
            allElements.addFirst(table(elements.toArray(new Element[0])));
            allElements.addFirst(h1("Sport Activities (" + activities.getFirst().sport.name + ")"));
        } else {
            allElements.addFirst(h1("No more results to show!"));
        }
        allElements.addFirst(br());
        allElements.addFirst(alink("/", "Home Page"));

        return html(
                head(
                        title("Sport Activities"),
                        style()
                ),
                body(
                        allElements.toArray(new Element[0])
                )
        ).toString();
    }

    private LinkedList<Element> getFooter(HashMap<String, LinkedList<String>> queryString,
                                          LinkedList<Activity> activities, String sportId) {
        LinkedList<Element> footer = new LinkedList<>();

        int skip = getSkip(queryString);
        int top = getTop(queryString);

        footer.add(br());

        if (skip > 0) {
            footer.add(
                    alink("/sports/" + sportId + "/activities?skip=" + Math.max(0, (skip - top))
                            + "&top=" + Math.max(0, top), "Previous Page"));
        }

        footer.add(alink("/sports/" + sportId, "Back to Sport"));

        if (top == activities.size()) {
            footer.add(
                    alink("/sports/" + sportId + "/activities?skip=" + (skip + top) + "&top=" + top,
                            "Next Page"));
        }


        return footer;
    }
}