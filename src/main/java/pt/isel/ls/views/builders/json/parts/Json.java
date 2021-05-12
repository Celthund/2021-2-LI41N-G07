package pt.isel.ls.views.builders.json.parts;
import pt.isel.ls.views.builders.json.JsonElement;


public class Json extends JsonElement {
    public Json(JsonElement ... elements ){
        super(elements);
    }

    public Json(String content ){
        super(content);
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
