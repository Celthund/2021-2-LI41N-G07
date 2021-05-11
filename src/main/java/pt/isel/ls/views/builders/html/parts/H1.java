package pt.isel.ls.views.builders.html.parts;


import pt.isel.ls.views.builders.html.Element;

public class H1 extends Element {

    public H1(Element... elements) {
        super(elements);
    }

    public H1(String content) {
        super(content);
    }

    @Override
    protected String beginTag() {
        return "<h1>";
    }

    @Override
    protected String endTag() {
        return "</h1>";
    }
}
