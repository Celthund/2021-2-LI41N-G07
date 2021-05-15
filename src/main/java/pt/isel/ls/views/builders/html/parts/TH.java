package pt.isel.ls.views.builders.html.parts;

import pt.isel.ls.views.builders.html.Element;

public class TH extends Element {
    public TH(Element... elements) {
        super(elements);
    }

    public TH(String content) {
        super(content);
    }

    @Override
    protected String beginTag() {
        return "<th>";
    }

    @Override
    protected String endTag() {
        return "</th>";
    }
}
