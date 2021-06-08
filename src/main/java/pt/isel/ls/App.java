package pt.isel.ls;

import java.util.Scanner;

import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.http.TimeServlet;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App {
    private static final int LISTEN_PORT = 8080;
    private static final Logger log = LoggerFactory.getLogger(App.class);


    public static void main(String[] args) {
        Init init = new Init();
        // Method that register the routes
        try {
            init.registerRoutes();
            init.registerViews();
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
                if (input.equalsIgnoreCase("LISTEN /")) {
                    try {
                        startServer(init);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    init.run(input);
                    System.out.print("> ");
                }
            }
        } else {
            StringBuilder input = new StringBuilder();
            for (String string : args) {
                input.append(string).append(" ");
            }
            init.run(input.toString());
        }

    }

    public static void startServer(Init init) throws Exception {

        log.info("main started");

        String portDef = System.getenv("PORT");
        int port = portDef != null ? Integer.parseInt(portDef) : LISTEN_PORT;
        log.info("configured listening port is {}", port);

        Server server = new Server(port);
        ServletHandler handler = new ServletHandler();
        TimeServlet servlet = new TimeServlet(init);

        handler.addServletWithMapping(new ServletHolder(servlet), "/*");
        log.info("registered {} on all paths", servlet);

        server.setHandler(handler);
        server.start();

        log.info("server started listening on port {}", port);
        server.join();

        log.info("main is ending");
    }
}
