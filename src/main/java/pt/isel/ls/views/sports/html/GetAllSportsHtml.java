package pt.isel.ls.views.sports.html;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.GetAllSportsResult;
import pt.isel.ls.views.PageNavigation;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;

public class GetAllSportsHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws InvalidJsonException {
        LinkedList<Sport> sports = ((GetAllSportsResult) requestResult).getData();
        LinkedList<Element> elements = new LinkedList<>();

        if (sports == null) {
            sports = new LinkedList<>();
        }

        elements.add(tr(
            th("Sid"),
            th("Name"),
            th("Description")

        ));

        for (Sport sport : sports) {
            elements.add(tr(
                td(a("/sports/" + sport.sid, Integer.toString(sport.sid))),
                td(sport.name),
                td(sport.description)
            ));
        }
        HashMap<String, LinkedList<String>> queryString = requestResult.getRequest().getQueryStrings();
        LinkedList<Element> allElements = getFooter(queryString, sports);
        allElements.addFirst(table(
                elements.toArray(new Element[0])
        ));
        allElements.addFirst(h1("Sports"));
        allElements.addFirst(br());
        allElements.addFirst(a("/", "HomePage"));

        Element html = html(
            head(
                title("Sports: ")
            ),
            body(
                    allElements.toArray(new Element[0])
                )
            );
        return html.toString();
    }
    private LinkedList<Element> getFooter(HashMap<String, LinkedList<String>> queryString, LinkedList<Sport> sports){
        LinkedList<Element> footer = new LinkedList<>();

        int skip = PageNavigation.getSkip(queryString);
        int top = PageNavigation.getTop(queryString);

        footer.add(br());
        if (skip + top < sports.size()){
            footer.add(a("/sports?skip=" + (skip + top) + "&top=" + top, "Next Page"));
            footer.add(br());
        }
        if(skip > 0) {
            footer.add(a("/sports?skip="+ (skip - top) + "&top=" + top, "Previous Page"));
        }

        return footer;
    }

}
