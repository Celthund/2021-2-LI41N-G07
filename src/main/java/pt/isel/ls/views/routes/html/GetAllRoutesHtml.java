package pt.isel.ls.views.routes.html;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.GetAllRoutesResult;
import static pt.isel.ls.views.PageNavigation.getSkip;
import static pt.isel.ls.views.PageNavigation.getTop;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;

public class GetAllRoutesHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws InvalidJsonException {
        LinkedList<Route> routes = ((GetAllRoutesResult) requestResult).getData();
        LinkedList<Element> elements = new LinkedList<>();

        if (routes == null) {
            routes = new LinkedList<>();
        }

        elements.add(tr(
            th("Route Id"),
            th("Start Location"),
            th("End Location"),
            th("Distance")

        ));

        HashMap<String, LinkedList<String>> queryString = requestResult.getRequest().getQueryStrings();
        LinkedList<Element> allElements = getFooter(queryString, routes);

        if (routes.size() > 0) {
            for (Route route : routes) {
                elements.add(tr(
                    td(alink("/routes/" + route.rid, Integer.toString(route.rid))),
                    td(route.startLocation),
                    td(route.endLocation),
                    td(Integer.toString(route.distance))
                ));
            }

            allElements.addFirst(table(
                elements.toArray(new Element[0])
            ));
        } else {
            allElements.addFirst(paragraph("No more results to show!"));
            allElements.addFirst(br());
        }
        allElements.addFirst(h1("Routes"));
        allElements.addFirst(br());
        allElements.addFirst(alink("/", "Home Page"));

        allElements.add(hr());
        allElements.add(
            form("/routes", "POST",
                h2("Add new route:"),
                paragraph("Start Location"),
                input("text", "startlocation", "startlocation", "", ""),
                paragraph("End Location"),
                input("text", "endlocation", "endlocation", "", ""),
                paragraph("Distance"),
                input("number", "distance", "distance", "", ""),
                input("submit", "", "", "", "")
            )
        );

        Element html = html(
            head(
                title("Routes"),
                style()
            ),
            body(
                allElements.toArray(new Element[0])
            )
        );

        return html.toString();
    }

    private LinkedList<Element> getFooter(HashMap<String, LinkedList<String>> queryString, LinkedList<Route> routes) {
        LinkedList<Element> footer = new LinkedList<>();

        int skip = getSkip(queryString);
        int top = getTop(queryString);

        footer.add(br());

        if (skip > 0) {
            footer
                .add(alink("/routes?skip=" + Math.max(0, (skip - top)) + "&top=" + Math.max(0, top), "Previous Page"));
        }

        if (top == routes.size()) {
            footer.add(alink("/routes?skip=" + (skip + top) + "&top=" + top, "Next Page"));
        }

        return footer;
    }
}
