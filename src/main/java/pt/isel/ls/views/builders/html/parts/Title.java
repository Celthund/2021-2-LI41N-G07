package pt.isel.ls.views.builders.html.parts;


import pt.isel.ls.views.builders.html.Element;

public class Title extends Element {

    public Title(Element... elements) {
        super(elements);
    }

    public Title(String content) {
        super(content);
    }

    @Override
    protected String beginTag() {
        return "<title>";
    }

    @Override
    protected String endTag() {
        return "</title>";
    }
}
