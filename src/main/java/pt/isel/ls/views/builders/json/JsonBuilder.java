package pt.isel.ls.views.builders.json;

import pt.isel.ls.exceptions.InvalidJsonException;
import pt.isel.ls.views.builders.json.parts.*;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class JsonBuilder {
    // Method that creates the Json method object by receiving JsonPut variables and sending to the
    //JsonElement Constructor
    public static JsonObject jsonObject(JsonPut... newObj) {
        return new JsonObject(newObj);
    }

    // This method is where we add values to the object, it has key and a value
    public static JsonPut jsonPut(Object key, Object value) throws InvalidJsonException {
        // List that will contain the key in the first position and the value in the second
        LinkedList<JsonElement> newJsonObject = new LinkedList<>();

        // If it receives null it will just write the string null if not it will check if its
        //any of the compatibles key types if not just throws an exeption
        if (key == null) {
            newJsonObject.add(new JsonKey("null"));
        } else if (key instanceof Number || key instanceof String || key instanceof Boolean) {
            newJsonObject.add(new JsonKey(key.toString()));
        } else {
            throw new InvalidJsonException("Invalid Key!");
        }
        String newVal = getValueString(value);
        newJsonObject.add(new JsonValue(newVal));

        // Returns a JsonPut so it can be store in the jSonObject method and be refactored accordingly
        //in toString() method of the super class (JsonElement)
        return new JsonPut(newJsonObject);
    }

    // It creates an Array of Json values
    public static JsonArray jsonArray(Object... values) throws InvalidJsonException {
        StringBuilder builder = new StringBuilder();

        // For every value in the parameters it checks for valid values and writes them
        //json style separating them by commas
        for (Object value : values) {
            builder.append(getValueString(value));
            builder.append(", ");
        }

        // Removes the final comma
        if(builder.length() > 2)
            builder.deleteCharAt(builder.length() - 2);

        // Returns the string with all the variables of the array
        return new JsonArray(builder.toString());
    }


    // Receives a Java object as parameters, checks if is a valid Json object and
    //writes it accordingly
    private static String getValueString(Object object) throws InvalidJsonException {
        // A null object in Json is represented by "null"
        if (object == null) {
            return "\"" + "null" + "\"";
        }
        // A number (int, double, float...) is represented just with the value
        if (object instanceof Number || object instanceof Boolean) {
            return object.toString();
        }
        // A String is represented with quotations
        if (object instanceof String || object instanceof Date) {
            return "\"" + object + "\"";
        }
        // If its an JsonElement it means its a JsonObject or a JsonArray, so it just calls
        //the toString() method of them that handles the formatting
        if (object instanceof JsonElement) {
            return object.toString();
        }
        // If it has a list as a parameters it will run through the list adding all the value
        //(separating them by commas) and returning has a string
        if (object instanceof List) {
            StringBuilder builder = new StringBuilder();
            for (Object listVal : (List) object) {
                builder.append(getValueString(listVal)).append(", ");
            }
            builder.deleteCharAt(builder.length() - 2);
            return builder.toString();
        }

        // If it reaches here it means its not a valid Json
        throw new InvalidJsonException("Invalid Value!");
    }
}
