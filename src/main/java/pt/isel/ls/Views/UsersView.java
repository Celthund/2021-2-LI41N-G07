package pt.isel.ls.Views;

import pt.isel.ls.Commands.RequestHandler;
import pt.isel.ls.Commands.RequestResult;
import pt.isel.ls.Exceptions.InvalidRequestException;
import pt.isel.ls.DataClass.User;
import pt.isel.ls.Models.UserModel;
import pt.isel.ls.Request.Method;
import pt.isel.ls.Request.Request;

import java.util.LinkedList;

// Class that sends the information it got to the corresponding model to handle
//showing the results to the user
public class UsersView implements RequestHandler {

    UserModel model = new UserModel();

    public RequestResult getUserById(String id) {
        User user = model.getUserById(id);
        if (user != null){
            return new RequestResult(
                    200,
                    user,
                    "Found user with id = " + id);
        }
        return new RequestResult(404, null, "User not found.");
    }

    public RequestResult getAllUsers() {
        LinkedList<User> users = model.getAllUsers();
        if(users.size() > 0){
            return new RequestResult(200, users, users.size() + " users found.");
        }
        return new RequestResult(404, null, "No users found.");
    }

    public RequestResult createUser(String name, String email) {
        User user = model.createRoute(name, email);
        if (user != null){
            return new RequestResult(200, user, "User created with success with id = " + user.id + "");
        }
        return new RequestResult(500, null, "Failed to create user.");
    }

    @Override
    public RequestResult execute(Request request) throws InvalidRequestException {
        if (request.getMethod() == Method.GET && request.getParameters().size() == 0)
            return getAllUsers();

        if (request.getMethod() == Method.GET && request.getParameters().containsKey("uid"))
            return getUserById(request.getParameters().get("uid"));

        if (request.getMethod() == Method.POST && request.getQueryString().containsKey("name") &&
                request.getQueryString().containsKey("email"))
            return createUser(request.getQueryString().get("name").getFirst(), request.getQueryString().get("email").getFirst());    //TO DO

        throw new InvalidRequestException();
    }
}
