package pt.isel.ls.views.builders.html.parts;

import pt.isel.ls.views.builders.html.Element;

public class A extends Element {
    private final String href;

    public A(String href, Element... elements) {
        super(elements);
        this.href = href;
    }

    public A(String href, String content) {
        super(content);
        this.href = href;
    }

    @Override
    protected String beginTag() {
        return "<a href=" + href + ">";
    }

    @Override
    protected String endTag() {
        return "</a>";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
