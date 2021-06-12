package pt.isel.ls.views.builders.html.parts;

import pt.isel.ls.views.builders.html.Element;

public class P extends Element {

    public P(Element... elements) {
        super(elements);
    }

    public P(String content) {
        super(content);
    }

    @Override
    protected String beginTag() {
        return "<p>";
    }

    @Override
    protected String endTag() {
        return "</p>";
    }
}
