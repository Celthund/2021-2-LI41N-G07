package pt.isel.ls.views;

import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.results.RequestResult;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;

public class RootHtml implements View{
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws AppException {
        return html(
                body(
                        a("/users", "Users"),
                        a("/sports", "Sports"),
                        a("/routes", "Routes")
                )
        ).toString();
    }
}
