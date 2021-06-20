package pt.isel.ls.views.builders.html.parts;


import pt.isel.ls.views.builders.html.Element;

public class Hr extends Element {

    public Hr(String content) {
        super(content);
    }

    @Override
    protected String beginTag() {
        return "";
    }

    @Override
    protected String endTag() {
        return "<hr>";
    }
}