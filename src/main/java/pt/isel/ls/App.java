package pt.isel.ls;

import pt.isel.ls.Commands.RequestHandler;
import pt.isel.ls.Commands.RequestResult;
import pt.isel.ls.Path.Router;

import java.util.Optional;

public class App {

    public static void main(String[] args) {

        Router test = new Router();


        try {
            test.addRoute("GET", "/", request -> {
                System.out.println("GET /");
                System.out.println(request);
                return null;
            });
            test.addRoute("GET", "/user/<id>", request -> {
                System.out.println("GET /user/<id>");
                System.out.println(request);
                return null;
            });
        } catch (RouteAlreadyExistsException e) {
            e.printStackTrace();
        }

        String ex = "GET / nome=Tiago&Numero=47199&Nome=Jorge"; //"POST /users/id name=First+Last&email=example@email.com";
        try {
            Request request = parseRequest(ex);
            Optional<RequestResult> result = test.findRoute(request);
        } catch (InvalidRequestException | RouteNotFoundException e) {
            e.printStackTrace();
        }


        String ex1 = "GET /user/batatas nome=Tiago&Numero=47199&Nome=Jorge"; //"POST /users/id name=First+Last&email=example@email.com";
        try {
            Request request = parseRequest(ex1);
            Optional<RequestResult> result = test.findRoute(request);
        } catch (InvalidRequestException | RouteNotFoundException e) {
            e.printStackTrace();
        }
       /* if(args.length > 1)
        {
            String[] nextCommands = new String[args.length - 1];

            for(int i = 1 ; i < args.length; i++)
                nextCommands[i - 1] = args[0];

            Method methods = new Method();
            MethodType method= methods.getMethodType(nextCommands[0]);

            if(method != null){

            }

            System.out.println("Cheio");
            Scanner in = new Scanner(System.in);
            String operation = "";
            while(operation.equalsIgnoreCase("exit")){
                //TODO
            }

        }
        else
        {
            System.out.println("args vazio");
        }*/

    }
    
    private static Request parseRequest(String request) throws InvalidRequestException {
        String[] arr = request.split(" ");
        
        if(arr.length < 2){ //doesn't have path, method or both
            throw new InvalidRequestException();
        }

        if(arr.length == 2){ // doesn't have parameters
            return new Request(arr[0], arr[1]);
        }

        return new Request(arr[0], arr[1], arr[2]);
    }

}
