package pt.isel.ls.views.builders.html.parts;

import pt.isel.ls.views.builders.html.Element;

public class TD extends Element {
    public TD(Element... elements) {
        super(elements);
    }

    public TD(String content) {
        super(content);
    }

    @Override
    protected String beginTag() {
        return "<td>";
    }

    @Override
    protected String endTag() {
        return "</td>";
    }

}
