package pt.isel.ls.views.builders.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Element {
    private final List<Element> elements;
    private final String content;

    public Element(Element... elements) {
        this.elements = Arrays.asList(elements);
        content = null;
    }

    public Element(String content) {
        elements = new ArrayList<>();
        this.content = content;
    }

    protected abstract String beginTag();

    protected abstract String endTag();

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(beginTag());

        if (content != null) {
            stringBuilder.append(content);
        } else {
            stringBuilder.append('\n');
        }
        for (Element element : elements) {
            stringBuilder.append(element.toString("\t"));
        }
        stringBuilder.append(endTag()).append('\n');
        return stringBuilder.toString();
    }

    private String toString(String tabs) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tabs).append(beginTag());

        if (content != null) {
            stringBuilder.append(content);
        } else {
            stringBuilder.append('\n');
        }
        for (Element element : elements) {
            stringBuilder.append(element.toString(tabs + "\t"));
        }
        if (elements.size() > 0) {
            stringBuilder.append(tabs);
        }
        stringBuilder.append(endTag()).append('\n');
        return stringBuilder.toString();
    }
}
