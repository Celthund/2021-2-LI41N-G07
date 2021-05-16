package pt.isel.ls;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Scanner;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.handlers.activities.*;
import pt.isel.ls.handlers.routes.CreateRouteHandler;
import pt.isel.ls.handlers.routes.GetAllRoutesHandler;
import pt.isel.ls.handlers.routes.GetRouteByIdHandler;
import pt.isel.ls.handlers.sports.CreateSportHandler;
import pt.isel.ls.handlers.sports.GetAllSportsHandler;
import pt.isel.ls.handlers.sports.GetSportByIdHandler;
import pt.isel.ls.handlers.users.CreateUserHandler;
import pt.isel.ls.handlers.users.GetAllUsersHandler;
import pt.isel.ls.handlers.users.GetUserByIdHandler;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.OptionResult;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.activities.*;
import pt.isel.ls.results.routes.CreateRouteResult;
import pt.isel.ls.results.routes.GetAllRoutesResult;
import pt.isel.ls.results.routes.GetRouteByIdResult;
import pt.isel.ls.results.sports.CreateSportResult;
import pt.isel.ls.results.sports.GetAllSportsResult;
import pt.isel.ls.results.sports.GetSportByIdResult;
import pt.isel.ls.results.users.CreateUserResult;
import pt.isel.ls.results.users.GetAllUsersResult;
import pt.isel.ls.results.users.GetUserByIdResult;
import pt.isel.ls.routers.HandlerRouter;
import pt.isel.ls.routers.ViewRouter;
import pt.isel.ls.views.OptionPlainText;
import pt.isel.ls.views.View;
import pt.isel.ls.views.activities.html.*;
import pt.isel.ls.views.activities.json.*;
import pt.isel.ls.views.activities.plaintext.*;
import pt.isel.ls.views.routes.html.CreateRouteHtml;
import pt.isel.ls.views.routes.html.GetAllRoutesHtml;
import pt.isel.ls.views.routes.html.GetRouteByIdHtml;
import pt.isel.ls.views.routes.json.CreateRouteJson;
import pt.isel.ls.views.routes.json.GetAllRoutesJson;
import pt.isel.ls.views.routes.json.GetRouteByIdJson;
import pt.isel.ls.views.routes.plaintext.CreateRoutePlainText;
import pt.isel.ls.views.routes.plaintext.GetAllRoutesPlainText;
import pt.isel.ls.views.routes.plaintext.GetRouteByIdPlainText;
import pt.isel.ls.views.sports.html.CreateSportHtml;
import pt.isel.ls.views.sports.html.GetAllSportsHtml;
import pt.isel.ls.views.sports.html.GetSportByIdHtml;
import pt.isel.ls.views.sports.json.CreateSportJson;
import pt.isel.ls.views.sports.json.GetAllSportsJson;
import pt.isel.ls.views.sports.json.GetSportByIdJson;
import pt.isel.ls.views.sports.plaintext.CreateSportPlainText;
import pt.isel.ls.views.sports.plaintext.GetAllSportsPlainText;
import pt.isel.ls.views.sports.plaintext.GetSportByIdPlainText;
import pt.isel.ls.views.users.html.CreateUserHtml;
import pt.isel.ls.views.users.html.GetAllUsersHtml;
import pt.isel.ls.views.users.html.GetUserByIdHtml;
import pt.isel.ls.views.users.json.CreateUserJson;
import pt.isel.ls.views.users.json.GetAllUsersJson;
import pt.isel.ls.views.users.json.GetUserByIdJson;
import pt.isel.ls.views.users.plaintext.CreateUserPlainText;
import pt.isel.ls.views.users.plaintext.GetAllUsersPlainText;
import pt.isel.ls.views.users.plaintext.GetUserByIdPlainText;

public class App {

    private static final HandlerRouter handlerRouter = new HandlerRouter();
    private static final ViewRouter viewRouter = new ViewRouter();


    public static void main(String[] args) {
        // Method that register the routes
        try {
            registerRoutes();
            registerViews();
        } catch (AppException e) {
            System.out.print(e.getMessage());
        }

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
            RequestHandler handler = handlerRouter.findRoute(request);
            Optional<RequestResult<?>> result = handler.execute(request);
            // If there is a RequestResult will show the result
            if (result.isPresent()) {
                String accept;
                if (request.getHeaders().containsKey("accept")) {
                    accept = request.getHeaders().get("accept").getFirst();
                } else {
                    accept = "text/html";
                }
                RequestResult requestResult = result.get();
                View view = viewRouter.findView(requestResult.getClass(), accept);

                // Get's the Representation (html, plaintext, or Json with the request result data)
                //properly formatted
                String res = view.getRepresentation(requestResult);
                // Gets the Headers dictionary form the request so it can check if the user
                //specified a file to be written, if not it will be written to the console
                HashMap<String, LinkedList<String>> headers = request.getHeaders();
                if (headers.containsKey("file-name")) {
                    PrintWriter printWriter = new PrintWriter(headers.get("file-name").getFirst());
                    // Writes the result to the file
                    printWriter.write(res);
                    // Closes the connection to the file
                    printWriter.close();
                } else {
                    // Writes the connection to the terminal as the default
                    System.out.println(res);
                }


            } else {
                // It means that the it didnt any result from the sending request
                System.out.println("Error getting result");
            }
        } catch (AppException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // Used to register the Routes
    private static void registerRoutes() throws AppException {
        handlerRouter.addRoute("OPTION", "/", request -> Optional.of(
            new OptionResult(200, null, handlerRouter.print()))
        );

        // Adds a Method and Path to a handler class
        handlerRouter.addRoute("GET", "/users", new GetAllUsersHandler());
        handlerRouter.addRoute("GET", "/users/{uid}", new GetUserByIdHandler());
        handlerRouter.addRoute("POST", "/users", new CreateUserHandler());

        handlerRouter.addRoute("GET", "/routes", new GetAllRoutesHandler());
        handlerRouter.addRoute("GET", "/routes/{rid}", new GetRouteByIdHandler());
        handlerRouter.addRoute("POST", "/routes", new CreateRouteHandler());

        handlerRouter.addRoute("GET", "/sports", new GetAllSportsHandler());
        handlerRouter.addRoute("GET", "/sports/{sid}", new GetSportByIdHandler());
        handlerRouter.addRoute("POST", "/sports", new CreateSportHandler());

        handlerRouter.addRoute("GET", "/sports/{sid}/activities", new GetActivitiesBySidHandler());
        handlerRouter.addRoute("GET", "/sports/{sid}/activities/{aid}", new GetActivityByAidSidHandler());
        handlerRouter.addRoute("GET", "/users/{uid}/activities", new GetActivitiesByUidHandler());
        handlerRouter.addRoute("GET", "/tops/activities", new GetActivitiesByTopsHandler());
        handlerRouter.addRoute("POST", "/sports/{sid}/activities", new CreateActivityHandler());
        handlerRouter.addRoute("DELETE", "/users/{uid}/activities", new DeleteActivitiesByUidAidHandler());
    }

    //Creates all the views
    private static void registerViews() throws AppException {

        // Links the result to the corresponding, so when a result is get, it can show the proper view

        //-----------------------------Creates the views for Plain Text--------------------------------------//

        viewRouter.addView(GetAllUsersResult.class, "text/plain", new GetAllUsersPlainText());
        viewRouter.addView(GetUserByIdResult.class, "text/plain", new GetUserByIdPlainText());
        viewRouter.addView(CreateUserResult.class, "text/plain", new CreateUserPlainText());

        viewRouter.addView(GetAllSportsResult.class, "text/plain", new GetAllSportsPlainText());
        viewRouter.addView(GetSportByIdResult.class, "text/plain", new GetSportByIdPlainText());
        viewRouter.addView(CreateSportResult.class, "text/plain", new CreateSportPlainText());

        viewRouter.addView(GetAllRoutesResult.class, "text/plain", new GetAllRoutesPlainText());
        viewRouter.addView(GetRouteByIdResult.class, "text/plain", new GetRouteByIdPlainText());
        viewRouter.addView(CreateRouteResult.class, "text/plain", new CreateRoutePlainText());

        viewRouter.addView(CreateActivityResult.class, "text/plain", new CreateActivityPlainText());
        viewRouter.addView(GetActivitiesByTopsResult.class, "text/plain", new GetActivitiesByTopsPlainText());
        viewRouter.addView(GetActivityByAidSidResult.class, "text/plain", new GetActivityByAidSidResultPlainText());
        viewRouter.addView(GetActivitiesBySidResult.class, "text/plain", new GetActivitiesBySidResultPlainText());
        viewRouter.addView(GetActivitiesByUidResult.class, "text/plain", new GetActivitiesByUidResultPlainText());
        viewRouter
            .addView(DeleteActivitiesByUidAidResult.class, "text/plain", new DeleteActivitiesByUidAidPlainText());

        //-----------------------------Creates the views for Json--------------------------------------//
        viewRouter.addView(CreateUserResult.class, "application/json", new CreateUserJson());
        viewRouter.addView(GetUserByIdResult.class, "application/json", new GetUserByIdJson());
        viewRouter.addView(GetAllUsersResult.class, "application/json", new GetAllUsersJson());

        viewRouter.addView(CreateSportResult.class, "application/json", new CreateSportJson());
        viewRouter.addView(GetSportByIdResult.class, "application/json", new GetSportByIdJson());
        viewRouter.addView(GetAllSportsResult.class, "application/json", new GetAllSportsJson());

        viewRouter.addView(CreateRouteResult.class, "application/json", new CreateRouteJson());
        viewRouter.addView(GetRouteByIdResult.class, "application/json", new GetRouteByIdJson());
        viewRouter.addView(GetAllRoutesResult.class, "application/json", new GetAllRoutesJson());

        viewRouter.addView(CreateActivityResult.class, "application/json", new CreateActivityJson());
        viewRouter.addView(GetActivityByAidSidResult.class, "application/json", new GetActivityByAidSidJson());
        viewRouter.addView(GetActivitiesBySidResult.class, "application/json", new GetActivitiesBySidJson());
        viewRouter.addView(GetActivitiesByUidResult.class, "application/json", new GetActivitiesByUidJson());
        viewRouter.addView(GetActivitiesByTopsResult.class, "application/json", new GetActivitiesByTopsJson());
        viewRouter
            .addView(DeleteActivitiesByUidAidResult.class, "application/json", new DeleteActivitiesByUidAidJson());


        //-----------------------------Creates the views for html--------------------------------------//
        viewRouter.addView(CreateUserResult.class, "text/html", new CreateUserHtml());
        viewRouter.addView(GetAllUsersResult.class, "text/html", new GetAllUsersHtml());
        viewRouter.addView(GetUserByIdResult.class, "text/html", new GetUserByIdHtml());

        viewRouter.addView(CreateSportResult.class, "text/html", new CreateSportHtml());
        viewRouter.addView(GetAllSportsResult.class, "text/html", new GetAllSportsHtml());
        viewRouter.addView(GetSportByIdResult.class, "text/html", new GetSportByIdHtml());

        viewRouter.addView(CreateRouteResult.class, "text/html", new CreateRouteHtml());
        viewRouter.addView(GetAllRoutesResult.class, "text/html", new GetAllRoutesHtml());
        viewRouter.addView(GetRouteByIdResult.class, "text/html", new GetRouteByIdHtml());

        viewRouter.addView(CreateActivityResult.class, "text/html", new CreateActivityHtml());
        viewRouter.addView(GetActivitiesBySidResult.class, "text/html", new GetActivitiesBySidHtml());
        viewRouter.addView(GetActivitiesByTopsResult.class, "text/html", new GetActivitiesByTopsHtml());
        viewRouter.addView(GetActivitiesByUidResult.class, "text/html", new GetActivitiesByUidHtml());
        viewRouter.addView(GetActivityByAidSidResult.class, "text/html", new GetActivityByAidSidHtml());
        viewRouter.addView(DeleteActivitiesByUidAidResult.class, "text/html", new DeleteActivitiesByUidAidHtml());

        //-----------------------------Creates the view for OPTION------------------------------------//
        viewRouter.addView(OptionResult.class, "text/html", new OptionPlainText());
    }
}
