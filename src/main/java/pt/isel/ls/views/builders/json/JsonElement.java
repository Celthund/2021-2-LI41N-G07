package pt.isel.ls.views.builders.json;

import java.util.ArrayList;
import java.util.List;
import static java.util.Arrays.asList;

public abstract class JsonElement {
    private final List<JsonElement> elements;
    private final String content;

    public JsonElement(JsonElement... elements) {
        this.elements = asList(elements);
        content = null;
    }

    public JsonElement(List<JsonElement> elements) {
        this.elements = elements;
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

        if (content != null) {
            stringBuilder.append('\t').append(content).append('\n');
        }

        for (JsonElement element : elements) {
            stringBuilder.append('\t').append(element.toStringObject("")).append(",\n");
        }

        if(stringBuilder.length() > 2)
            stringBuilder.deleteCharAt(stringBuilder.length() - 2);

        stringBuilder.append(endTag());
        return stringBuilder.toString();
    }

    private String toStringObject(String tabs) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tabs).append(beginTag());

        if (content != null) {

            if (content.contains("{") || content.contains("[")) {
                stringBuilder.append(content.replace("\n", "\n\t"));
            } else {
                stringBuilder.append(content);
            }
        }

        for (JsonElement element : elements) {
            stringBuilder.append(element.toStringObject(""));
        }

        stringBuilder.append(tabs).append(endTag());
        return stringBuilder.toString();
    }


}
