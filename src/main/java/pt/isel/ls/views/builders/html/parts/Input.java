package pt.isel.ls.views.builders.html.parts;

import pt.isel.ls.views.builders.html.Element;

public class Input extends Element {
    String type;
    String id;
    String name;
    String value;

    public Input(String type, String id, String name, String value, Element... elements) {

        super(elements);
        this.type = type;
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public Input(String type, String id, String name, String value, String content) {

        super(content);
        this.type = type;
        this.id = id;
        this.name = name;
        this.value = value;
    }

    @Override
    protected String beginTag() {
        StringBuilder beginTag = new StringBuilder();
        beginTag.append("<input");

        if (type.length() > 0) {
            beginTag.append(" type=\"" + type + "\"");
        }

        if (id.length() > 0) {
            beginTag.append(" id=\"" + id + "\"");
        }

        if (name.length() > 0) {
            beginTag.append(" name=\"" + name + "\"");
        }
        if (value.length() > 0) {
            beginTag.append(" value=\"" + value + "\"");
        }

        beginTag.append(">");

        return beginTag.toString();
    }

    @Override
    protected String endTag() {
        return "</input>";
    }
}
