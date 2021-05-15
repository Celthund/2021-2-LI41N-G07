package pt.isel.ls.views.builders.plaintext;

import pt.isel.ls.models.domainclasses.User;

public class PlainTextGetter {

    public static String getUserPlainText (User user){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("User {id: ")
                .append(user.id)
                .append(" , name: ")
                .append(user.name)
                .append(" , email: ")
                .append(user.email).append("}\n");

        return stringBuilder.toString();
    }
}
