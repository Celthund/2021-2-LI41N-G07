package pt.isel.ls.views.builders.html.parts;

import pt.isel.ls.views.builders.html.Element;

public class DT extends Element {
    public DT(Element... elements) {
        super(elements);
    }

    public DT(String content) {
        super(content);
    }

    @Override
    protected String beginTag() {
        return "<dt>";
    }

    @Override
    protected String endTag() {
        return "</dt>";
    }
}
