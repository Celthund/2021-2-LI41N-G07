package pt.isel.ls.views.activities.html;

import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.GetActivitiesBySidResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;

import java.util.HashMap;
import java.util.LinkedList;

import static pt.isel.ls.views.PageNavigation.*;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;
import static pt.isel.ls.views.builders.html.HtmlGetter.getActivityHtmlTableHeader;
import static pt.isel.ls.views.builders.html.HtmlGetter.getActivityHtmlTableRow;

public class GetActivitiesBySidHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws AppException {
        LinkedList<Activity> activities = ((GetActivitiesBySidResult) requestResult).getData();

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
        allElements.addFirst(br());
        allElements.addFirst(a("/", "HomePage"));

        return html(
                head(
                        title("Activities")
                ),
                body(
                        allElements.toArray(new Element[0])
                )
        ).toString();
    }

    private LinkedList<Element> getFooter(HashMap<String, LinkedList<String>> queryString, LinkedList<Activity> activities) {
        int skip = getSkip(queryString);
        int top = getTop(queryString);

        LinkedList<Element> footer = new LinkedList<>();
        footer.add(br());
        footer.add(a("/sports/" + activities.getFirst().sport.sid, "Back to Sports"));

        if (skip + top < activities.size()){
            footer.add(a("/sports/" + activities.getFirst().sport.sid + "?skip=" + (skip + top) + "&top=" + top, "Next Page"));
            footer.add(br());
        }
        if(skip > 0) {
            footer.add(a("/sports/"+ activities.getFirst().sport.sid + "?skip="+ (skip - top) + "&top=" + top, "Previous Page"));
        }

        return footer;
    }
}