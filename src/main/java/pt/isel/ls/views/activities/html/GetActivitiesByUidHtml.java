package pt.isel.ls.views.activities.html;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivitiesByUidResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;
import static pt.isel.ls.views.builders.html.HtmlGetter.getActivityHtmlTableHeader;
import static pt.isel.ls.views.builders.html.HtmlGetter.getActivityHtmlTableRow;

public class GetActivitiesByUidHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws AppException {
        LinkedList<Activity> activities = ((GetActivitiesByUidResult) requestResult).getData();

        if (activities == null) {
            activities = new LinkedList<>();
        }

        LinkedList<Element> elements = new LinkedList<>(getActivityHtmlTableHeader());

        for (Activity activity : activities) {
            elements.add(tr(getActivityHtmlTableRow(activity).toArray(new Element[0])));
        }

        HashMap<String, LinkedList<String>> queryString = requestResult.getRequest().getQueryStrings();

        LinkedList<Element> allElements = getFooter(queryString, activities);
        allElements.addFirst(table(elements.toArray(new Element[0])));
        allElements.addFirst(h1("Activities"));

        return html(
            head(
                title("Activities")
            ),
            body(
                allElements.toArray(new Element[0])
            )
        ).toString();
    }

    private LinkedList<Element> getFooter(HashMap<String, LinkedList<String>> queryString,
                                          LinkedList<Activity> activities) {
        int skip = 0;
        int top = 5;
        if (queryString.containsKey("skip")) {
            skip = Integer.parseInt(queryString.get("skip").getFirst());
        }
        if (queryString.containsKey("top")) {
            top = Integer.parseInt(queryString.get("top").getFirst());
        }

        LinkedList<Element> footer = new LinkedList<>();

        footer.add(br());
        footer.add(alink("/users/" + activities.getFirst().user.id + "?skip=" + (skip + top) + "&top=" + top, "Next"));
        if (skip > 0) {
            footer.add(
                alink("/users/" + activities.getFirst().user.id + "?skip=" + (skip - top) + "&top=" + top, "Previous"));
        }

        return footer;
    }
}