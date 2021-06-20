package pt.isel.ls.views.builders.html.parts;

import pt.isel.ls.views.builders.html.Element;

public class Form extends Element {
    String action;
    String method;

    public Form(String action, String method, Element... elements) {

        super(elements);
        this.action = action;
        this.method = method;
    }

    public Form(String action, String method, String content) {

        super(content);
        this.action = action;
        this.method = method;
    }

    @Override
    protected String beginTag() {
        StringBuilder beginTag = new StringBuilder();
        beginTag.append("<form");

        if (action.length() > 0) {
            beginTag.append(" action=\"" + action + "\"");
        }

        if (method.length() > 0) {
            beginTag.append(" method=\"" + method + "\"");
        }

        beginTag.append(">");

        return beginTag.toString();
    }

    @Override
    protected String endTag() {
        return "</form>";
    }
}
