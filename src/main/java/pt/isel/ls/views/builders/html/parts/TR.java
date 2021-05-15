package pt.isel.ls.views.builders.html.parts;

import pt.isel.ls.views.builders.html.Element;

public class TR extends Element {
    public TR(Element... elements) {
        super(elements);
    }

    public TR(String content) {
        super(content);
    }

    @Override
    protected String beginTag() {
        return "<tr>";
    }

    @Override
    protected String endTag() {
        return "</tr>";
    }
}
