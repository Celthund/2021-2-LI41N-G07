package pt.isel.ls.views.builders.json.parts;
import pt.isel.ls.views.builders.json.JsonElement;

public class JsonObject extends JsonElement {

    public JsonObject(String content){
        super(content);
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
