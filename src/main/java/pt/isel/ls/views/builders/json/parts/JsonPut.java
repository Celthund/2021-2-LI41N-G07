package pt.isel.ls.views.builders.json.parts;

import pt.isel.ls.views.builders.json.JsonElement;
import java.util.LinkedList;

public class JsonPut extends JsonElement {

    public JsonPut(LinkedList<JsonElement> json) {
        super(json);
    }

    @Override
    protected String beginTag() {
        return "";
    }

    @Override
    protected String endTag() {
        return "";
    }
}
