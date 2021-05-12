package pt.isel.ls.views.users.json;

import pt.isel.ls.views.builders.json.JsonElement;

import static pt.isel.ls.views.builders.json.JsonBuilder.*;

public class CreateUserJson {

    static JsonElement element1 = newJson(
            put("User1", 1),
            put(1, 2),
            put(1, "1User"),
            put("User1", "User1"),
            put(null, null),
            put(null, 2)
    );
    static JsonElement element2 = newJson(
            put("jorge", 2),
            put(3, 2),
            put(2, "user"),
            put("string1", "string2")
    );

    public static void print(){
        System.out.println(element1);
    }

}
