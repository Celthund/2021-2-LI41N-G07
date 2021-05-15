package pt.isel.ls.views.builders.html.parts;


import pt.isel.ls.views.builders.html.Element;

public class LI extends Element {

    public LI(Element... elements) {
        super(elements);
    }

    public LI(String content) {
        super(content);
    }

    @Override
    protected String beginTag() {
        return "<li>";
    }

    @Override
    protected String endTag() {
        return "</li>";
    }
}
