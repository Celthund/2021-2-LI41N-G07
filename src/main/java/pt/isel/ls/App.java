package pt.isel.ls;

import pt.isel.ls.Request;
import pt.isel.ls.utils.Methods.Method;
import pt.isel.ls.utils.Methods.MethodType;

import java.lang.invoke.WrongMethodTypeException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        String ex = "POST /users/id name=First+Last&email=example@email.com";
        try {
            System.out.println(parseRequest(ex));
        } catch (InvalidRequestException e) {
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
        
        if(arr.length < 2){
            throw new InvalidRequestException();
        }

        if(arr.length == 2){
            return new Request(arr[0], arr[1]);
        }

        return new Request(arr[0], arr[1], arr[2]);
    }

}
