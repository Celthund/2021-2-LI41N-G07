package pt.isel.ls.views.builders.json.parts;

import java.util.LinkedList;
import pt.isel.ls.views.builders.json.JsonElement;

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
