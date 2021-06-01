package pt.isel.ls.views.builders.html;

import pt.isel.ls.views.builders.html.parts.*;

public class HtmlBuilder {

    public static Html html(Element... elements) {
        return new Html(elements);
    }

    public static Html html(String content) {
        return new Html(content);
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

    public static Title title(Element... elements) {
        return new Title(elements);
    }

    public static Title title(String content) {
        return new Title(content);
    }

    public static UL ul(Element... elements) {
        return new UL(elements);
    }

    public static UL ul(String content) {
        return new UL(content);
    }

    public static LI li(Element... elements) {
        return new LI(elements);
    }

    public static LI li(String content) {
        return new LI(content);
    }

    public static Table table(String content) {
        return new Table(content);
    }

    public static Table table(Element... elements) {
        return new Table(elements);
    }

    public static TR tr(Element... elements) {
        return new TR(elements);
    }

    public static TD td(String content) {
        return new TD(content);
    }

    public static TH th(String content) {
        return new TH(content);
    }

    public static TH th(Element... elements) {
        return new TH(elements);
    }

    public static DD dd(Element... elements) {
        return new DD(elements);
    }

    public static DD dd(String content) {
        return new DD(content);
    }

    public static DL dl(Element... elements) {
        return new DL(elements);
    }

    public static DL dl(String content) {
        return new DL(content);
    }

    public static DT dt(Element... elements) {
        return new DT(elements);
    }

    public static DT dt(String content) {
        return new DT(content);
    }

    public static A a(String href, Element... elements) {
        return new A(href, elements);
    }

    public static A a(String href, String content) {
        return new A(href, content);
    }

    public static Br br() {
        return new Br("");
    }

}
