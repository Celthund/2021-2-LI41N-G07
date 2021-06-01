package pt.isel.ls.views;

import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.results.RequestResult;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;

public class RootHtml implements View{
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws AppException {
        return html(
                body(
                        a("/users?skip=0&top=1", "Users"),
                        a("/sports?skip=0&top=1", "Sports"),
                        a("/routes?skip=0&top=1", "Routes")
                )
        ).toString();
    }
}
