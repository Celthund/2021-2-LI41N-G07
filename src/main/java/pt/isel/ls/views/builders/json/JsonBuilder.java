package pt.isel.ls.views.builders.json;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.views.builders.json.parts.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JsonBuilder {
    public static JsonObject jsonObject(JsonPut ... newObj){
        return new JsonObject(newObj);
    }

    public static JsonPut jsonPut(Object key, Object value) throws InvalidJsonException {
        LinkedList<JsonElement> newJsonObject = new LinkedList<>();
        if(key == null){
            newJsonObject.add(new JsonKey("null"));
        }
        if(key instanceof Number || key instanceof String || key instanceof Boolean){
            newJsonObject.add(new JsonKey(key.toString()));
        }
        else{
            throw new InvalidJsonException("Invalid Key!");
        }

        String newVal = getValueString(value);
        newJsonObject.add(new JsonValue(newVal));

        return new JsonPut(newJsonObject);
    }

    public static JsonArray jsonArray(Object ... values) throws InvalidJsonException {
        StringBuilder builder = new StringBuilder();

        for (Object value : values) {
            builder.append(getValueString(value));
            builder.append(", ");
        }
        builder.deleteCharAt(builder.length() - 2);
        return new JsonArray(builder.toString());
    }


    private static String getValueString(Object object) throws InvalidJsonException {
        if(object == null)
            return "\"" + "null" + "\"";
        if (object instanceof Number || object instanceof Boolean)
            return object.toString();
        if (object instanceof String)
            return "\"" + object + "\"";
        if(object instanceof JsonElement)
            return object.toString();
        if (object instanceof List) {
            StringBuilder builder = new StringBuilder();
            for (Object listVal : (List) object) {
                builder.append(getValueString(listVal)).append(", ");
            }
            builder.deleteCharAt(builder.length() - 2);
            return builder.toString();
        }

        throw new InvalidJsonException("Invalid Value!");
    }
}
