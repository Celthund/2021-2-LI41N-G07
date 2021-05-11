package pt.isel.ls.views.builders.html.parts;

import pt.isel.ls.views.builders.html.Element;

public class HTML extends Element {

    public HTML(Element... elements) {
        super(elements);
    }

    public HTML(String content) {
        super(content);
    }

    @Override
    protected String beginTag() {
        return "<html>";
    }

    @Override
    protected String endTag() {
        return "</html>";
    }
}
