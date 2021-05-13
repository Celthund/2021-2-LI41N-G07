package pt.isel.ls.views.builders.json;

import pt.isel.ls.views.builders.json.parts.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JsonBuilder {
    public static JsonObject jsonObject(JsonPut ... newObj){
        return new JsonObject(newObj);
    }

    public static JsonPut jsonPut(Object key, Object value){
        LinkedList<JsonElement> newJsonObject = new LinkedList<>();
        if(key == null){
            newJsonObject.add(new JsonKey("null"));
        }
        if(key instanceof Number || key instanceof String || key instanceof Boolean){
            newJsonObject.add(new JsonKey(key.toString()));
        }
        else{
            System.out.println("WRONG KEY PROPERTY!");
            System.exit(-1);
        }

        String newVal = getValueString(value);
        if(newVal == null) {
            System.out.println("WRONG VALUE PROPERTY!");
            System.exit(-1);
        } else{
            newJsonObject.add(new JsonValue(newVal));
        }

        return new JsonPut(newJsonObject);
    }

    public static JsonArray jsonArray(Object ... values){
        StringBuilder builder = new StringBuilder();

        for (Object value : values) {
            String newVal = getValueString(value);
            if(newVal == null) {
                if(value instanceof List){
                    for (Object listVal : (List)value) {
                        builder.append(getValueString(listVal)).append(", ");
                    }
                }
                else {
                    System.out.println("WRONG VALUE PROPERTY!");
                    System.exit(-1);
                }
            }
            if(newVal != null){
                builder.append(newVal);
            }
            builder.append(", ");
        }
        builder.deleteCharAt(builder.length() - 2);
        return new JsonArray(builder.toString());
    }


    private static String getValueString(Object object) {
        if(object == null)
            return "\"" + "null" + "\"";
        if (object instanceof Number || object instanceof Boolean)
            return object.toString();
        if (object instanceof String)
            return "\"" + object + "\"";
        if(object instanceof JsonElement)
            return object.toString();

        return null;
    }
}
