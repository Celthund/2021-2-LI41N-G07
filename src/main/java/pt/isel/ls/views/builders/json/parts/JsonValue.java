package pt.isel.ls.views.builders.json.parts;

import java.util.List;
import pt.isel.ls.views.builders.json.JsonElement;

public class JsonValue extends JsonElement {

    public JsonValue(String content) {
        super(content);
    }

    public JsonValue(List<JsonElement> elements) {
        super(elements);
    }

    public JsonValue(JsonElement... elements) {
        super(elements);
    }

    @Override
    protected String beginTag() {
        return ":";
    }

    @Override
    protected String endTag() {
        return "";
    }
}
