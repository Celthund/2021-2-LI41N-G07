package pt.isel.ls;

import java.util.Scanner;
import pt.isel.ls.exceptions.AppException;


public class App {

    public static void main(String[] args) {
        CustomServer customServer = new CustomServer();
        // Method that register the routes
        try {
            customServer.registerRoutes();
            customServer.registerViews();
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
                customServer.run(input);
                System.out.print("> ");
            }
        } else {
            StringBuilder input = new StringBuilder();
            for (String string : args) {
                input.append(string).append(" ");
            }
            customServer.run(input.toString());
        }
        customServer.waitForServer();
    }


}
