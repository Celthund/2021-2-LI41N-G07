package pt.isel.ls.views.html;

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
        stringBuilder.append(beginTag()).append('\n');

        if(content != null){
            stringBuilder.append('\t').append(content).append('\n');
        }

        for (Element element: elements) {
            stringBuilder.append(element.toString("\t"));
        }

        stringBuilder.append(endTag()).append('\n');
        return stringBuilder.toString();
    }

    private String toString(String tabs) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tabs).append(beginTag()).append('\n');

        if(content != null){
            stringBuilder.append(tabs).append('\t').append(content).append('\n');
        }

        for (Element element: elements) {
            stringBuilder.append(element.toString(tabs + "\t"));
        }

        stringBuilder.append(tabs).append(endTag()).append('\n');
        return stringBuilder.toString();
    }
}
