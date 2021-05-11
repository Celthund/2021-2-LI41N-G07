package pt.isel.ls.views.html;

import pt.isel.ls.views.html.parts.*;

public class HTMLBuilder {

    public static HTML html(Element... elements) {
        return new HTML(elements);
    }
    public static HTML html(String content) {
        return new HTML(content);
    }

    public static Head head(Element... elements) {
        return new Head(elements);
    }

    public static Head head(String content) {
        return new Head(content);
    }

    public static Body body(Element... elements) {
        return new Body(elements);
    }

    public static Body body(String content) {
        return new Body(content);
    }

    public static H1 h1(Element... elements) {
        return new H1(elements);
    }

    public static H1 h1(String content) {
        return new H1(content);
    }

}
