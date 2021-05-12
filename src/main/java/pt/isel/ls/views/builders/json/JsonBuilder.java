package pt.isel.ls.views.builders.json;

import pt.isel.ls.views.builders.html.Element;
import pt.isel.ls.views.builders.json.parts.Json;
import pt.isel.ls.views.builders.json.parts.JsonArray;
import pt.isel.ls.views.builders.json.parts.JsonObject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JsonBuilder {


    public static Json createJson(JsonElement... elements) {
        return new Json(elements);
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
        if(object instanceof JsonArray)
            return ((JsonArray)object).getArray();
        if(object instanceof JsonElement)
            return object.toString().replace("\n", "\n\t");

        return "\"" + "null" + "\"";
    }
    public static JsonElement put(Object key, Object value){
        return new JsonObject( "\"" + (key != null? key : "null") + "\"" + ":" + getString(value));
    }
    public static JsonArray createJsonArray(Object ... requestedValues){
        List<Object> listOfValues = Arrays.asList(requestedValues);
        StringBuilder builder = new StringBuilder();

        for (Object value : listOfValues) {
            builder.append(getString(value)).append(", ");
        }
        if(builder.length() > 1)
            builder.deleteCharAt(builder.length() - 2);

        return new JsonArray(builder.toString());
    }

}
