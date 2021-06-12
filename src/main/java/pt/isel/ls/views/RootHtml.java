package pt.isel.ls.views;

import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.results.RequestResult;
import static pt.isel.ls.views.builders.html.HtmlBuilder.*;

public class RootHtml implements View {
    @Override
    public String getRepresentation(RequestResult<?> requestResult) throws AppException {
        int skip = 0;
        int top = 5;
        // Homepage View
        return html(
            body(
                h1("Home"),
                h2("Grupo 7:"),
                paragraph(li("Jorge Simões - 46973"),
                    li("Luis Alves - 46974"),
                    li("Tiago Silva - 47199")),
                ul(alink("/users?skip=" + skip + "&top=" + top, "Users")),
                ul(alink("/sports?skip=" + skip + "&top=" + top, "Sports")),
                ul(alink("/routes?skip=" + skip + "&top=" + top, "Routes"))
            )
        ).toString();
    }
}
