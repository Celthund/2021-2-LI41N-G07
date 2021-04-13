package pt.isel.ls.Views;

import pt.isel.ls.Commands.RequestHandler;
import pt.isel.ls.Commands.RequestResult;
import pt.isel.ls.InvalidRequest;
import pt.isel.ls.Request.Method;
import pt.isel.ls.Request.Request;

public class Users implements RequestHandler {

    public RequestResult getUserById(String id) {
        return null;
    }

    public RequestResult getAllUsers() {
        return null;
    }

    public RequestResult createUser(String name, String email) {
        return null;
    }

    @Override
    public RequestResult execute(Request request) throws InvalidRequest {
        if (request.getMethod() == Method.GET && request.getParameters().size() == 0)
            return getAllUsers();

        if (request.getMethod() == Method.GET && request.getParameters().containsKey("id"))
            return getUserById(request.getParameters().get("id"));

        if (request.getMethod() == Method.POST && request.getQueryString().containsKey("name") &&
                request.getQueryString().containsKey("email"))
            return createUser(request.getQueryString().get("name").getFirst(), request.getQueryString().get("email").getFirst());    //TO DO

        throw new InvalidRequest();
    }
}
