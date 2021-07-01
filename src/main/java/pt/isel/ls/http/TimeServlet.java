package pt.isel.ls.http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Init;
import pt.isel.ls.exceptions.*;
import pt.isel.ls.request.Request;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.views.View;
import pt.isel.ls.views.builders.html.Element;

import static pt.isel.ls.views.builders.html.HtmlBuilder.*;

import static pt.isel.ls.views.builders.html.HtmlBuilder.html;
import static pt.isel.ls.views.builders.html.HtmlGetter.emptyDataSetHtml;

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
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(req.getMethod()).append(" ").append(req.getRequestURI());
            String s;
            if ((s = req.getQueryString()) != null) {
                stringBuilder.append(" ").append(headersToString(req)).append(" ").append(s);
            }
            Request request = new Request(stringBuilder.toString());
            handleRequest(request, resp);
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("incoming request: method={}, uri={}, accept={}",
            req.getMethod(),
            req.getRequestURI(),
            req.getHeader("Accept"));

        try {
            byte[] bytes = new byte[req.getContentLength()];
            req.getInputStream().read(bytes);
            String queryString = new String(bytes);
            if (queryString.length() == 0 && req.getQueryString() != null) {
                queryString = req.getQueryString();
            } else if (req.getQueryString() != null) {
                queryString += "&" + req.getQueryString();
            }
            String s = req.getMethod() + " " + req.getRequestURI() + " "
                + headersToString(req) + " " + queryString;
            Request request = new Request(s);
            handleRequest(request, resp);
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        }

        resp.setStatus(303);
        resp.addHeader("location", "/users/56");
    }


    public void handleRequest(Request request, HttpServletResponse resp) throws IOException {
        try {
            // Finds the route that match the request sent
            RequestHandler requestHandler = init.findRoute(request);
            Optional<RequestResult<?>> result = requestHandler.execute(request);
            if (result.isPresent()) {
                String accept;
                // Checks for any headers to see the user pretends any specific header
                if (request.getHeaders().containsKey("Accept")) {
                    accept = request.getHeaders().get("Accept").getFirst().split(",")[0];
                } else {
                    // Default header
                    accept = "text/html";
                }

                RequestResult<?> requestResult = result.get();
                // Sets the request sent initially in case its needed later
                requestResult.setRequest(request);

                // Gets the status of the request
                resp.setStatus(requestResult.getStatus());
                Charset utf8 = StandardCharsets.UTF_8;

                resp.setContentType(String.format(accept + "; charset=%s", utf8.name()));

                for (Map.Entry<String, String> entry : requestResult.getHeaders().entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    resp.addHeader(key, value);
                }
                // Finds the view that corresponds to the request it got
                View view = init.findView(requestResult, accept);
                String respBody = view.getRepresentation(requestResult);
                byte[] respBodyBytes = respBody.getBytes(utf8);
                resp.setContentLength(respBodyBytes.length);
                OutputStream os = resp.getOutputStream();
                os.write(respBodyBytes);
                os.flush();
                log.info("outgoing response: method={}, uri={}, status={}, Content-Type={}",
                    request.getMethod(),
                    String.join("/", request.getPath()),
                    resp.getStatus(),
                    resp.getHeader("Content-Type"));
            }
        } catch (AppException e) {
            Charset utf8 = StandardCharsets.UTF_8;
            if (RouteNotFoundException.class.equals(e.getClass())) {
                resp.setStatus(404);
            } else if (BadRequestException.class.equals(e.getClass()) ||
                InvalidRequestException.class.equals(e.getClass())) {
                resp.setStatus(400);
            } else if (ServerErrorException.class.equals(e.getClass())) {
                resp.setStatus(500);
            }

            resp.setContentType(String.format("text/html; charset=%s", utf8.name()));

            byte[] respBodyBytes = emptyDataSetHtml(e.getMessage()).toString().getBytes(utf8);
            // If it gets here it means something went wrong with the request so we send a bad request status

            resp.setContentLength(respBodyBytes.length);
            OutputStream os = resp.getOutputStream();
            os.write(respBodyBytes);
            os.flush();
        }
    }


    private String headersToString(HttpServletRequest req) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();
        //accept:text/plain|file-name:users.txt
        for (Iterator<String> it = req.getHeaderNames().asIterator(); it.hasNext(); ) {
            String s = it.next();
            if (stringBuilder.length() > 0) {
                stringBuilder.append("|");
            }
            stringBuilder.append(s).append(":").append(
                URLEncoder.encode(req.getHeader(s), StandardCharsets.UTF_8.toString()));
        }
        return stringBuilder.toString();
    }

}