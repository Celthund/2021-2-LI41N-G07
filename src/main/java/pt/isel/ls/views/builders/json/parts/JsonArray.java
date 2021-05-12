package pt.isel.ls.views.builders.json.parts;


import pt.isel.ls.views.builders.html.Element;
import pt.isel.ls.views.builders.json.JsonElement;

import java.util.Arrays;

public class JsonArray extends JsonElement {
    String content;
    public JsonArray(JsonElement... elements) {
        super(elements);
    }

    public JsonArray(String content) {
        super(content);
        this.content = content;

    }

    public String getArray(){
        return beginTag() + content + endTag();
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
