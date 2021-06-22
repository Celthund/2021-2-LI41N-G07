package pt.isel.ls.views.sports.html;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Sport;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.sports.GetAllSportsResult;
import static pt.isel.ls.views.PageNavigation.getSkip;
import static pt.isel.ls.views.PageNavigation.getTop;
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

        HashMap<String, LinkedList<String>> queryString = requestResult.getRequest().getQueryStrings();
        LinkedList<Element> allElements = getFooter(queryString, sports);

        if (sports.size() > 0) {
            for (Sport sport : sports) {
                elements.add(tr(
                    td(alink("/sports/" + sport.sid, Integer.toString(sport.sid))),
                    td(sport.name),
                    td(sport.description)
                ));
            }
            allElements.addFirst(table(
                elements.toArray(new Element[0])
            ));
        } else {
            allElements.addFirst(paragraph("No more results to show!"));
            allElements.addFirst(br());
        }
        allElements.addFirst(h1("Sports"));
        allElements.addFirst(br());
        allElements.addFirst(alink("/", "Home Page"));

        allElements.add(hr());
        allElements.add(
            form("/sports", "POST",
                h2("Add new Sport:"),
                paragraph("Name"),
                input("text", "name", "name", "", ""),
                paragraph("Description"),
                input("text", "description", "description", "", ""),
                input("submit", "", "", "", "")
            )
        );

        Element html = html(
            head(
                title("Sports: "),
                style()
            ),
            body(
                allElements.toArray(new Element[0])
            )
        );
        return html.toString();
    }

    private LinkedList<Element> getFooter(HashMap<String, LinkedList<String>> queryString, LinkedList<Sport> sports) {
        LinkedList<Element> footer = new LinkedList<>();

        int skip = getSkip(queryString);
        int top = getTop(queryString);

        footer.add(br());

        if (skip > 0) {
            footer
                .add(alink("/sports?skip=" + Math.max(0, (skip - top)) + "&top=" + Math.max(0, top), "Previous Page"));
        }

        if (top == sports.size()) {
            footer.add(alink("/sports?skip=" + (skip + top) + "&top=" + top, "Next Page"));
        }
        return footer;
    }

}
