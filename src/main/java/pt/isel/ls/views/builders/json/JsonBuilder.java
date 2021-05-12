package pt.isel.ls.views.builders.json;

import pt.isel.ls.views.builders.html.Element;
import pt.isel.ls.views.builders.json.parts.Json;
import pt.isel.ls.views.builders.json.parts.JsonObject;

public class JsonBuilder {


    public static Json newJson(JsonElement... elements) {
        return new Json(elements);
    }

    public static Json put(String content) {
        return new Json(content);
    }



    /*
    public static JsonObject put(String key, String value) {
        key = key != null ? ("\"" + key + "\"") : "null";
        value = value != null ? ("\"" + value + "\"") : "null";
        return new JsonObject(key + " : " + value);
    }

    public static JsonObject put(Number key, String value) {
        String keyString = key != null ? ("\"" + key + "\"") : "null";
        value = value != null ? ("\"" + value + "\"") : "null";
        return new JsonObject(keyString + " : " + value);
    }

    public static JsonObject put(String key, Number value) {
        key = key != null ? ("\"" + key + "\"") : "null";
        String valueString = value != null ? value.toString() : "null";
        return new JsonObject(key + " : " + valueString);
    }

    public static JsonObject put(Number value, Number key){
        String valueString = value != null ? ("\"" + value + "\"") : "null";
        String keyString = key != null ? key.toString() : "null";
        return new JsonObject(valueString + " : " + keyString);
    }
    */
    private static String getString(Object object) {
        if (object instanceof Number || object instanceof Boolean)
            return object.toString();
        if (object instanceof String)
            return "\"" + object + "\"";
        return "\"" + "null" + "\"";
    }
    public static JsonObject put(Object key, Object value){
        return new JsonObject((key != null ? "\"" + key + "\"" : "null") + " : " + getString(value));
    }
}
