package pt.isel.ls.views.builders.html.parts;


import pt.isel.ls.views.builders.html.Element;

public class UL extends Element {

    public UL(Element... elements) {
        super(elements);
    }

    public UL(String content) {
        super(content);
    }

    @Override
    protected String beginTag() {
        return "<ul>";
    }

    @Override
    protected String endTag() {
        return "</ul>";
    }
}
