package pt.isel.ls.views.builders.html.parts;


import pt.isel.ls.views.builders.html.Element;

public class Br extends Element {

    public Br(String content) {
        super(content);
    }

    @Override
    protected String beginTag() {
        return "";
    }

    @Override
    protected String endTag() {
        return "<br>";
    }
}