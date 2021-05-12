package pt.isel.ls.views.builders.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

public abstract class JsonElement {
    private final List<JsonElement> elements;
    private final String content;

    public JsonElement(JsonElement... elements) {
        this.elements = asList(elements);
        content = null;
    }
    public JsonElement(String content) {
        elements = new ArrayList<>();
        this.content = content;
    }

    protected abstract String beginTag();

    protected abstract String endTag();

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(beginTag()).append('\n');

        for (JsonElement element : elements) {
            stringBuilder.append(element.toString(",\n"));
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 2).append(endTag());
        return stringBuilder.toString();
    }

    private String toString(String tabs) {
        StringBuilder stringBuilder = new StringBuilder();

        if (content != null) {
            stringBuilder.append(" ").append(content);
        }

        for (JsonElement element : elements) {
            stringBuilder.append(element.toString(tabs));
        }

        stringBuilder.append(tabs);
        return stringBuilder.toString();
    }
}
