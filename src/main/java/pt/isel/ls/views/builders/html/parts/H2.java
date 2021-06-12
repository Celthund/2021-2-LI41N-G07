package pt.isel.ls.views.builders.html.parts;


import pt.isel.ls.views.builders.html.Element;

public class H2 extends Element {

    public H2(Element... elements) {
        super(elements);
    }

    public H2(String content) {
        super(content);
    }

    @Override
    protected String beginTag() {
        return "<h2>";
    }

    @Override
    protected String endTag() {
        return "</h2>";
    }
}
