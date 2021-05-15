package pt.isel.ls.views.builders.html.parts;

import pt.isel.ls.views.builders.html.Element;

public class DD extends Element {
    public DD(Element... elements) {
        super(elements);
    }

    public DD(String content) {
        super(content);
    }

    @Override
    protected String beginTag() {
        return "<dd>";
    }

    @Override
    protected String endTag() {
        return "</dd>";
    }
}
