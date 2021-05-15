package pt.isel.ls.views.builders.json.parts;

import pt.isel.ls.views.builders.json.JsonElement;

public class JsonArray extends JsonElement {
    public JsonArray(String content) {
        super(content);
    }

    @Override
    protected String beginTag() {
        return "[";
    }
    @Override
    protected String endTag() {
        return "]";
    }
}
