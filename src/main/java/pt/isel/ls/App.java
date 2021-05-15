package pt.isel.ls;

import pt.isel.ls.exceptions.AppException;
import java.util.Optional;
import java.util.Scanner;
import pt.isel.ls.routers.HandlerRouter;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.OptionResult;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.*;
import pt.isel.ls.results.routes.*;
import pt.isel.ls.results.activities.*;
import pt.isel.ls.results.sports.*;
import pt.isel.ls.handlers.activities.*;
import pt.isel.ls.handlers.sports.*;
import pt.isel.ls.handlers.routes.*;
import pt.isel.ls.handlers.users.*;
import pt.isel.ls.routers.ViewRouter;
import pt.isel.ls.views.OptionPlainText;
import pt.isel.ls.views.View;
import pt.isel.ls.views.activities.json.*;
import pt.isel.ls.views.routes.html.CreateRouteHtml;
import pt.isel.ls.views.routes.html.GetAllRoutesHtml;
import pt.isel.ls.views.routes.html.GetRouteByIdHtml;
import pt.isel.ls.views.routes.json.CreateRouteJson;
import pt.isel.ls.views.routes.json.GetAllRoutesJson;
import pt.isel.ls.views.routes.json.GetRouteByIdJson;
import pt.isel.ls.views.sports.html.CreateSportHtml;
import pt.isel.ls.views.sports.html.GetAllSportsHtml;
import pt.isel.ls.views.sports.html.GetSportByIdHtml;
import pt.isel.ls.views.sports.json.CreateSportJson;
import pt.isel.ls.views.sports.json.GetAllSportsJson;
import pt.isel.ls.views.sports.json.GetSportByIdJson;
import pt.isel.ls.views.sports.plaintext.*;
import pt.isel.ls.views.users.html.CreateUserHtml;
import pt.isel.ls.views.users.html.GetAllUsersHtml;
import pt.isel.ls.views.users.html.GetUserByIdHtml;
import pt.isel.ls.views.users.json.CreateUserJson;
import pt.isel.ls.views.users.json.GetAllUsersJson;
import pt.isel.ls.views.users.json.GetUserByIdJson;
import pt.isel.ls.views.users.plaintext.*;

public class App {

    private static final HandlerRouter handlerRouter = new HandlerRouter();
    private static final ViewRouter viewRouter = new ViewRouter();


    public static void main(String[] args) {
        // Method that register the routes
        try {
            registerRoutes();
            registerViews();
        } catch (AppException e) {
            e.printStackTrace();
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
            Optional<RequestResult> result = handler.execute(request);
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
                System.out.println(view.getRepresentation(requestResult));
            } else {
                System.out.println("Error getting result");
            }
        } catch (AppException e) {
            System.out.println(e.getMessage());
        }
    }

    // Used to register the Routes
    private static void registerRoutes() throws AppException {
        handlerRouter.addRoute("OPTION", "/", request -> Optional.of(
            new OptionResult(200, null, handlerRouter.print()))
        );

        handlerRouter.addRoute("GET", "/users", new GetAllUsersHandler());
        handlerRouter.addRoute("GET", "/users/{uid}", new GetUserByIdHandler());
        handlerRouter.addRoute("POST", "/users", new CreateUserHandler());

        handlerRouter.addRoute("GET", "/routes", new GetAllRoutesHandler());
        handlerRouter.addRoute("GET", "/routes/{rid}", new GetRouteByIdHandler());
        handlerRouter.addRoute("POST", "/routes", new CreateRouteHandler());

        handlerRouter.addRoute("GET", "/sports", new GetAllSportsHandler());
        handlerRouter.addRoute("GET", "/sports/{sid}", new GetSportByIdHandler());
        handlerRouter.addRoute("POST", "/sports", new CreateSportHandler());

        handlerRouter.addRoute("GET", "/sports/{sid}/activities", new GetActivityBySidHandler());
        handlerRouter.addRoute("GET", "/sports/{sid}/activities/{aid}", new GetActivityByAidSidHandler());
        handlerRouter.addRoute("GET", "/users/{uid}/activities", new GetActivityByUidHandler());
        handlerRouter.addRoute("GET", "/tops/activities", new GetActivitiesByTopsHandler());
        handlerRouter.addRoute("POST", "/sports/{sid}/activities", new CreateActivityHandler());
    }

    //Creates all the views
    private static void registerViews() throws AppException {

        //-----------------------------Creates the views for Plain Text--------------------------------------//

        viewRouter.addView(GetAllUsersResult.class, "text/plain", new GetAllUsersPlainText());
        viewRouter.addView(GetUserByIdResult.class, "text/plain", new GetUserByIdPlainText());
        viewRouter.addView(CreateUserResult.class, "text/plain", new CreateUserPlainText());

        viewRouter.addView(GetAllSportsResult.class, "text/plain", new GetAllSportsPlainText());
        viewRouter.addView(GetSportByIdResult.class, "text/plain", new GetSportByIdPlainText());
        viewRouter.addView(CreateSportResult.class, "text/plain", new CreateSportPlainText());


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
        viewRouter.addView(GetActivityBySidResult.class, "application/json", new GetActivityBySidJson());
        viewRouter.addView(GetActivityByUidResult.class, "application/json", new GetActivityByUidJson());
        viewRouter.addView(GetActivitiesByTopsResult.class, "application/json", new GetActivitiesByTopsJson());



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



        //-----------------------------Creates the view for OPTION------------------------------------//
        viewRouter.addView(OptionResult.class, "text/html", new OptionPlainText());
    }
}
