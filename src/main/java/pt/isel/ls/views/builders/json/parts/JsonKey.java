package pt.isel.ls.views.builders.json.parts;
import pt.isel.ls.views.builders.json.JsonElement;

public class JsonKey extends JsonElement {

    public JsonKey(String content) {
        super(content);
    }

    @Override
    protected String beginTag() {
        return "\"";
    }

    @Override
    protected String endTag() {
        return "\"";
    }
}
