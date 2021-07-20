package pt.isel.ls.views.builders.json;

import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.List;

public abstract class JsonElement {
    // List to store the elements, the elements are one of the Json class
    private final List<JsonElement> elements;
    private final String content;

    // Constructor to receive an indeterminate array of elements
    public JsonElement(JsonElement... elements) {
        this.elements = asList(elements);
        content = null;
    }

    // Constructor to receive alink list of elements
    public JsonElement(List<JsonElement> elements) {
        this.elements = elements;
        content = null;
    }

    // // Constructor to receive alink string that will be written in the json object
    public JsonElement(String content) {
        elements = new ArrayList<>();
        this.content = content;
    }

    protected abstract String beginTag();

    protected abstract String endTag();

    // Formats the text to match an Json object
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

        if (stringBuilder.length() > 2) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        }

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
