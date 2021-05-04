package pt.isel.ls;

import pt.isel.ls.commands.RequestResult;
import pt.isel.ls.Path.Router;
import pt.isel.ls.Request.Request;
import pt.isel.ls.commands.RequestHandler;
import pt.isel.ls.handlers.activities.*;
import pt.isel.ls.handlers.routes.CreateRouteHandler;
import pt.isel.ls.handlers.routes.GetAllRoutesHandler;
import pt.isel.ls.handlers.routes.GetRouteByIdHandler;
import pt.isel.ls.handlers.sports.CreateSportHandler;
import pt.isel.ls.handlers.sports.GetAllSportsHandler;
import pt.isel.ls.handlers.sports.GetSportByIdHandler;
import pt.isel.ls.handlers.users.*;
import pt.isel.ls.exceptions.AppException;
import java.util.Optional;
import java.util.Scanner;

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
            RequestHandler handler = routing.findRoute(request);
            Optional<RequestResult> result = handler.execute(request);
            // If there is a RequestResult will show the result
            if (result.isPresent()) {
                System.out.println(result.get().message);
                // In case there was an error
                if (result.get().data != null) {
                    System.out.println(result.get().data);
                }

            } else {
                System.out.println("Error getting result");
            }
        } catch (AppException e) {
            System.out.println(e.getMessage());
        }
    }

    // Used to register the Routes
    private static void registerRoutes() {
        try {
            routing.addRoute("OPTION", "/", request -> Optional.of(new RequestResult(200, null, routing.print())));
            routing.addRoute("GET", "/", request -> {
                System.out.println("GET /");
                System.out.println(request);
                return Optional.empty();
            });

            routing.addRoute("GET", "/users", new GetAllUsersHandler());
            routing.addRoute("GET", "/users/{uid}", new GetUserByIdHandler());
            routing.addRoute("POST", "/users", new CreateUserHandler());

            routing.addRoute("GET", "/routes", new GetAllRoutesHandler());
            routing.addRoute("GET", "/routes/{rid}", new GetRouteByIdHandler());
            routing.addRoute("POST", "/routes", new CreateRouteHandler());

            routing.addRoute("GET", "/sports", new GetAllSportsHandler());
            routing.addRoute("GET", "/sports/{sid}", new GetSportByIdHandler());
            routing.addRoute("POST", "/sports", new CreateSportHandler());

            routing.addRoute("GET", "/sports/{sid}/activities", new GetActivityBySidHandler());
            routing.addRoute("GET", "/sports/{sid}/activities/{aid}", new GetActivityByAidSidHandler());
            routing.addRoute("GET", "/users/{uid}/activities", new GetActivityByUidHandler());
            routing.addRoute("GET", "/tops/activities", new GetActivitiesByTopsHandler());
            routing.addRoute("POST", "/sports/{sid}/activities", new CreateActivityHandler());

        } catch (AppException e) {
            System.out.println(e.getMessage());
        }


    }
}
