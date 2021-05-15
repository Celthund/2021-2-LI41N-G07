package pt.isel.ls.views.builders.html.parts;

import pt.isel.ls.views.builders.html.Element;

public class DL extends Element {
    public DL(Element... elements) {
        super(elements);
    }

    public DL(String content) {
        super(content);
    }

    @Override
    protected String beginTag() {
        return "<dl>";
    }

    @Override
    protected String endTag() {
        return "</dl>";
    }
}
