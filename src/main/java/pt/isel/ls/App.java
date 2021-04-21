package pt.isel.ls;

import pt.isel.ls.Commands.RequestResult;
import pt.isel.ls.Exceptions.InvalidRequestException;
import pt.isel.ls.Exceptions.RouteAlreadyExistsException;
import pt.isel.ls.Exceptions.RouteNotFoundException;
import pt.isel.ls.Path.Router;
import pt.isel.ls.Request.Method;
import pt.isel.ls.Request.Request;
import pt.isel.ls.Views.RoutesView;
import pt.isel.ls.Views.SportsView;
import pt.isel.ls.Views.UsersView;

import java.util.Optional;
import java.util.Scanner;

public class App {

    private static Router routing = new Router();

    public static void main(String[] args) {
        // Method that register the routes
        registerRoutes();

        // Checks the args length to see if it will enter user mode or execute the command right away
        if (args.length == 0) {
            Scanner scanner = new Scanner(System.in);
            String input;
            while(!(input = scanner.nextLine()).equalsIgnoreCase("EXIT /") ) {
                try {
                    // Sets the request with the arguments sent by the user
                    Request request = parseRequest(input);
                    // Finds the route through the request created and returns a RequestResult
                    Optional<RequestResult> result = routing.findRoute(request);
                    // If there is a RequestResult will show the result
                    if(result.isPresent()){
                        System.out.println(result.get().message);
                        // In case there was an error
                        if (result.get().data != null)
                            System.out.println(result.get().data);
                    } else {
                        System.out.println("Error getting result");
                    }
                } catch (RouteNotFoundException | InvalidRequestException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Used to register the Routes
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

            RoutesView route = new RoutesView();
            routing.addRoute("GET", "/routes", route);
            routing.addRoute("GET", "/routes/{rid}", route);
            routing.addRoute("POST", "/routes", route);

            SportsView sport = new SportsView();
            routing.addRoute("GET", "/sports", sport);
            routing.addRoute("GET", "/sports/{sid}", sport);
            routing.addRoute("POST", "/sports", sport);

        } catch (RouteAlreadyExistsException e) {
            e.printStackTrace();
        }
        
        
    }

    // Creates a Request to store the parsed request sent by the user
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
