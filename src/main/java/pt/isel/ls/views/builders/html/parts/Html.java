package pt.isel.ls.views.builders.html.parts;

import pt.isel.ls.views.builders.html.Element;

public class Html extends Element {

    public Html(Element... elements) {
        super(elements);
    }

    public Html(String content) {
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
