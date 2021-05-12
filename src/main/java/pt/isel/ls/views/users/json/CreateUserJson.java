package pt.isel.ls.views.users.json;

import pt.isel.ls.views.builders.json.JsonElement;

import static pt.isel.ls.views.builders.json.JsonBuilder.*;

public class CreateUserJson {

    static JsonElement element1 = createJson(
            put("User1", 1),
            put("wtf is happening", createJsonArray("ola", 1, true))
    );


    static JsonElement element2 = createJson(
            put("elemento", element1)
    );

    static JsonElement element3 = createJson(
            put("elemento", element1),
            put(true, element1),
            put("jorge", createJsonArray("value", 2, true))
    );

    public static void print(){
        System.out.println(element3);
    }


}
