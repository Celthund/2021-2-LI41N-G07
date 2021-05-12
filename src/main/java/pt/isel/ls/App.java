package pt.isel.ls;

import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.results.OptionResult;
import pt.isel.ls.results.users.CreateUserResult;
import pt.isel.ls.results.users.GetAllUsersResult;
import pt.isel.ls.results.users.GetUserByIdResult;
import pt.isel.ls.routers.HandlerRouter;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
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
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.routers.ViewRouter;
import pt.isel.ls.views.OptionPlainText;
import pt.isel.ls.views.View;
import pt.isel.ls.views.users.html.CreateUserHtml;
import pt.isel.ls.views.users.json.CreateUserJson;
import pt.isel.ls.views.users.plaintext.CreateUserPlainText;
import pt.isel.ls.views.users.plaintext.GetAllUsersPlainText;
import pt.isel.ls.views.users.plaintext.GetUserByIdPlainText;

public class App {

    private static final HandlerRouter handlerRouter = new HandlerRouter();
    private static final ViewRouter viewRouter = new ViewRouter();


    public static void main(String[] args) {
        // Method that register the routes
        try {
            CreateUserResult a = new CreateUserResult(1, new User("TesteUser ", "usermail@mail.com", 2),"MsgUserTeste");
            System.out.println(new CreateUserHtml().getRepresentation(a));
            CreateUserJson.print();
            CreateUserResult b = new CreateUserResult(1, new User("Json ", "usermail@mail.com", 2),"MsgUserTeste");

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

    private static void registerViews() throws AppException {
        viewRouter.addView(GetAllUsersResult.class, "text/plain", new GetAllUsersPlainText());
        viewRouter.addView(GetUserByIdResult.class, "text/plain", new GetUserByIdPlainText());
        viewRouter.addView(CreateUserResult.class, "text/plain", new CreateUserPlainText());

        // Little trick for Options
        viewRouter.addView(OptionResult.class, "text/html", new OptionPlainText());
    }
}
