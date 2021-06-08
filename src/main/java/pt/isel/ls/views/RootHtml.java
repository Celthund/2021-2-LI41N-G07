package pt.isel.ls.views;

import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.results.RequestResult;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;

public class RootHtml implements View{
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws AppException {
        int skip = 0;
        int top = 5;

        return html(
                body(
                        a("/users?skip="  + skip + "&top=" + top, "Users"),
                        a("/sports?skip=" + skip + "&top=" + top, "Sports"),
                        a("/routes?skip=" + skip + "&top=" + top, "Routes")
                )
        ).toString();
    }
}
