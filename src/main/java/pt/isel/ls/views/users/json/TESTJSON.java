package pt.isel.ls.views.users.json;

import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.CreateUserResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.json.JsonElement;
import pt.isel.ls.views.builders.json.parts.JsonObject;
import pt.isel.ls.views.builders.json.parts.JsonPut;

import java.util.LinkedList;

import static pt.isel.ls.views.builders.json.JsonBuilder.*;

public class TESTJSON {

    static JsonElement user1 = jsonObject(
            jsonPut("ID", 1),
            jsonPut("Name", "User1"),
            jsonPut("Email", "user1@email.com")
    );
    static JsonElement user2 = jsonObject(
            jsonPut("ID", 2),
            jsonPut("Name", "User2"),
            jsonPut("Email", "user2@email.com")
    );



    public static void print(){
        LinkedList<Object> a = new LinkedList<>();
        a.add(user1);
        a.add(user2);
        JsonElement json = jsonObject(
                jsonPut("Users",jsonArray(a))
        );
        System.out.println(json);
    }
}
