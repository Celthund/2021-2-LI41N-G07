package pt.isel.ls.views.builders.json.parts;

import pt.isel.ls.views.builders.json.JsonElement;

import java.util.List;

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
