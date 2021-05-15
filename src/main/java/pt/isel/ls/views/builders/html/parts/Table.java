package pt.isel.ls.views.builders.html.parts;

import pt.isel.ls.views.builders.html.Element;

public class Table extends Element{

    public Table(Element... elements) {
        super(elements);
    }

    public Table(String content) {
        super(content);
    }

    @Override
    protected String beginTag() {
        return "<table>";
    }

    @Override
    protected String endTag() {
        return "</table>";
    }
}
