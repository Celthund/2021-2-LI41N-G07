package pt.isel.ls.views.builders.json.parts;

import pt.isel.ls.views.builders.json.JsonElement;
import java.util.List;

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
