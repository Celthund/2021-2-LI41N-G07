package pt.isel.ls.views.builders.json.parts;


import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.views.builders.html.Element;
import pt.isel.ls.views.builders.json.JsonElement;

public class JsonObject extends JsonElement {

    public JsonObject(String content){
        super(content);
    }

/*
    public JsonObject(Element elements, String content){
        super((elements != null ? elements.toString() : "null")
                + " : "
                + "\"" + (content != null ? content : "null") + "\""
        );
    }

    public JsonObject(String content, Element elements){
        super((content != null ? content : "null")
                + " : "
                + "\"" + (elements != null ? elements.toString() : "null") + "\""
        );
    }
 */


    @Override
    protected String beginTag() {
        return " ";
    }

    @Override
    protected String endTag() {
        return "";
    }
}
