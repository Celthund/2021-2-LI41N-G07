package pt.isel.ls.views.builders.html.parts;


import pt.isel.ls.views.builders.html.Element;

public class Body extends Element {

    public Body(Element... elements) {
        super(elements);
    }

    public Body(String content) {
        super(content);
    }

    @Override
    protected String beginTag() {
        return "<body>";
    }

    @Override
    protected String endTag() {
        return "</body>";
    }
}
