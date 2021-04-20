package pt.isel.ls;

import pt.isel.ls.Commands.RequestResult;
import pt.isel.ls.Exceptions.InvalidRequestException;
import pt.isel.ls.Exceptions.RouteAlreadyExistsException;
import pt.isel.ls.Exceptions.RouteNotFoundException;
import pt.isel.ls.Path.Router;
import pt.isel.ls.Request.Method;
import pt.isel.ls.Request.Request;
import pt.isel.ls.Views.UsersView;

import java.util.Optional;
import java.util.Scanner;

public class App {

    private static Router routing = new Router();

    public static void main(String[] args) {
        registerRoutes();
        if (args.length == 0) {
            Scanner scanner = new Scanner(System.in);
            String input;
            while(!(input = scanner.nextLine()).equals("EXIT /") ) {
                try {
                    Request request = parseRequest(input);
                    Optional<RequestResult> result = routing.findRoute(request);
                    if(result.isPresent()){
                        System.out.println(result.get().message);
                        if (result.get().data != null)
                            System.out.println(result.get().data.toString());
                    } else {
                        System.out.println("Não deu! Errou! Tente novamente!");
                    }
                } catch (RouteNotFoundException | InvalidRequestException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private static void registerRoutes(){
        try {
            routing.addRoute("GET", "/", request -> {
                System.out.println("GET /");
                System.out.println(request);
                return null;
            });
            UsersView user = new UsersView();
            routing.addRoute("GET", "/users", user);
            routing.addRoute("GET", "/users/{uid}", user);
            routing.addRoute("POST", "/users", user);
        } catch (RouteAlreadyExistsException e) {
            e.printStackTrace();
        }
        
        
    }

    private static Request parseRequest(String request) throws InvalidRequestException {
        String[] arr = request.split(" ");

        if (arr.length < 2) { //doesn't have path, method or both
            throw new InvalidRequestException();
        }

        if (arr.length == 2) { // doesn't have parameters
            return new Request(Method.getMethod(arr[0]), arr[1]);
        }

        return new Request(Method.getMethod(arr[0]), arr[1], arr[2]);
    }

}
