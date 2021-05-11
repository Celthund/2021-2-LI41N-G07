package pt.isel.ls.views.html.parts;


import pt.isel.ls.views.html.Element;

public class Head extends Element {

    public Head(Element... elements) {
        super(elements);
    }

    public Head(String content) {
        super(content);
    }

    @Override
    protected String beginTag() {
        return "<head>";
    }

    @Override
    protected String endTag() {
        return "</head>";
    }
}
