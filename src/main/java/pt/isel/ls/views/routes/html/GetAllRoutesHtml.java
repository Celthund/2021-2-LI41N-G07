package pt.isel.ls.views.routes.html;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.models.domainclasses.Activity;
import pt.isel.ls.models.domainclasses.Route;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.routes.GetAllRoutesResult;
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

        for (Route route : routes) {
            elements.add(tr(
                td(a("/router/" + route.rid, Integer.toString(route.rid))),
                td(route.startLocation),
                td(route.endLocation),
                td(Integer.toString(route.distance))
            ));
        }

        Element html = html(
            head(
                title("Routes:")
            ),
            body(
                    br(),
                    a("/", "HomePage"),
                br(),
                table(
                    elements.toArray(new Element[0])
                )
            )
        );

        return html.toString();
    }

    private LinkedList<Element> getFooter(HashMap<String, LinkedList<String>> queryString, LinkedList<Activity> activities){
        int skip = 0;
        int top = 5;
        if(queryString.containsKey("skip")){
            skip = Integer.parseInt(queryString.get("skip").getFirst());
        }
        if(queryString.containsKey("top")){
            top = Integer.parseInt(queryString.get("top").getFirst());
        }

        LinkedList<Element> footer = new LinkedList<>();

        footer.add(br());

        footer.add(a("/sports/"+ activities.getFirst().sport.sid + "?skip=0&top=5", "Back to Sports Sid"));
        footer.add(a("/sports/"+ activities.getFirst().sport.sid +"/activities/?skip=0&top=5", "Back to Sports Activities"));

        footer.add(a("/sports/"+ activities.getFirst().sport.sid + "/activities/" + activities.getFirst().aid +"?skip="+ (skip + top) + "&top=" + top, "Next"));
        if(skip > 0) {
            footer.add(a("/sports/"+ activities.getFirst().sport.sid +"?skip="+ (skip - top) + "&top=" + top, "Previous"));
        }

        return footer;
    }
}
