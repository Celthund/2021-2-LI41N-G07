package pt.isel.ls.http;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Init;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.views.View;

public class TimeServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(TimeServlet.class);
    private final Init init;

    public TimeServlet(Init init) {
        super();
        this.init = init;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        log.info("incoming request: method={}, uri={}, accept={}",
            req.getMethod(),
            req.getRequestURI(),
            req.getRequestURI());

        try {
            // Creates a new Request based on what was received in the HttpServletRequest
            Request request = new Request(req.getMethod() + " " + req.getRequestURI() + " " + req.getQueryString());
            // Finds the route that match the request sent
            RequestHandler requestHandler = init.findRoute(request);
            Optional<RequestResult<?>> result = requestHandler.execute(request);
            if (result.isPresent()) {
                String accept;
                // Checks for any headers to see the user pretends any specific header
                if (request.getHeaders().containsKey("Accept")) {
                    accept = request.getHeaders().get("Accept").getFirst();
                } else {
                    // Default header
                    accept = "text/html";
                }

                RequestResult<?> requestResult = result.get();
                // Finds the view that corresponds to the request it got
                View view = init.findView(requestResult, accept);

                // Sets the request sent initially in case its needed later
                requestResult.setRequest(request);
                String respBody = view.getRepresentation(requestResult);

                // Gets the status of the request
                resp.setStatus(requestResult.getStatus());
                Charset utf8 = StandardCharsets.UTF_8;

                resp.setContentType(String.format(accept + "; charset=%s", utf8.name()));

                byte[] respBodyBytes = respBody.getBytes(utf8);
                resp.setContentLength(respBodyBytes.length);
                OutputStream os = resp.getOutputStream();
                os.write(respBodyBytes);
                os.flush();
                log.info("outgoing response: method={}, uri={}, status={}, Content-Type={}",
                    req.getMethod(),
                    req.getRequestURI(),
                    resp.getStatus(),
                    resp.getHeader("Content-Type"));
            }
        } catch (AppException e) {
            Charset utf8 = StandardCharsets.UTF_8;

            resp.setContentType(String.format("text/plain; charset=%s", utf8.name()));

            byte[] respBodyBytes = e.getMessage().getBytes(utf8);
            resp.setContentLength(respBodyBytes.length);
            OutputStream os = resp.getOutputStream();
            os.write(respBodyBytes);
            os.flush();
            // If it gets here it means something went wrong with the request so we send a bad request status
            resp.setStatus(400);
        }
    }
}