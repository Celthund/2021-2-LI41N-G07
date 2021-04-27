package pt.isel.ls;

import pt.isel.ls.Commands.RequestResult;
import pt.isel.ls.Path.Router;
import pt.isel.ls.Request.Request;
import pt.isel.ls.Views.ActivitiesView;
import pt.isel.ls.Views.RoutesView;
import pt.isel.ls.Views.SportsView;
import pt.isel.ls.Views.UsersView;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.Optional;
import java.util.Scanner;

import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Exceptions.RouteAlreadyExistsException;

public class App {

    private static Router routing = new Router();

    public static void main(String[] args) {
        // Method that register the routes
        registerRoutes();

        // Checks the args length to see if it will enter user mode or execute the command right away
        if (args.length == 0) {
            System.out.print("> ");
            Scanner scanner = new Scanner(System.in);
            String input;
            while (!(input = scanner.nextLine()).equalsIgnoreCase("EXIT /")) {
                run(input);
                System.out.print("> ");
            }
        } else {
            StringBuilder input = new StringBuilder();
            for (String string : args) {
                input.append(string).append(" ");
            }
            run(input.toString());
        }

    }

    private static void run(String input) {
        try {
            // Sets the request with the arguments sent by the user
            Request request = new Request(input);
            // Finds the route through the request created and returns a RequestResult
            Optional<RequestResult> result = routing.findRoute(request);
            // If there is a RequestResult will show the result
            if (result.isPresent()) {
                System.out.println(result.get().message);
                // In case there was an error
                if (result.get().data != null)
                    System.out.println(result.get().data);
            } else {
                System.out.println("Error getting result");
            }
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    // Used to register the Routes
    private static void registerRoutes() {
        try {
            routing.addRoute("OPTION", "/", request -> new RequestResult(200, null, routing.print()));
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

            ActivitiesView activity = new ActivitiesView();
            routing.addRoute("GET", "/sports/{sid}/activities", activity);
            routing.addRoute("GET", "/sports/{sid}/activities/{aid}", activity);
            routing.addRoute("GET", "/users/{uid}/activities", activity);
            routing.addRoute("GET", "/tops/activities", activity);
            routing.addRoute("POST", "/sports/{sid}/activities", activity);


        } catch (RouteAlreadyExistsException e) {
            e.printStackTrace();
        }
    }
}
