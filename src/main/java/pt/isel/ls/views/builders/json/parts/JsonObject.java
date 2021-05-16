package pt.isel.ls.views.builders.json.parts;

import java.util.List;
import pt.isel.ls.views.builders.json.JsonElement;

public class JsonObject extends JsonElement {

    public JsonObject(JsonElement... elements) {
        super(elements);
    }

    public JsonObject(List<JsonElement> elements) {
        super(elements);
    }

    @Override
    protected String beginTag() {
        return "{";
    }

    @Override
    protected String endTag() {
        return "}";
    }
}
