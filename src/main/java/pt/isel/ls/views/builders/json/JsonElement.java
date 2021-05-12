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
    protected JsonElement(String ... contents){
        elements = new ArrayList<>();
        List<String> values = Arrays.asList(contents);
        String tmp = "";
        for (String value : values) {
            tmp += " " + value + ",";
        };
        content = tmp.substring(0, tmp.length() -1);
    }

    protected abstract String beginTag();

    protected abstract String endTag();

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(beginTag()).append('\n');

        if (content != null) {
            stringBuilder.append('\t').append(content);
        }

        for (JsonElement element : elements) {
            stringBuilder.append(element.toString(""));
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 2).append(endTag());
        return stringBuilder.toString();
    }

    private String toString(String tabs) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tabs).append(beginTag());

        if (content != null) {
            stringBuilder.append(content);
        }

        for (JsonElement element : elements) {
            stringBuilder.append(element.toString(""));
        }

        stringBuilder.append(tabs).append(endTag()).append("\n");
        return stringBuilder.toString();
    }
}
