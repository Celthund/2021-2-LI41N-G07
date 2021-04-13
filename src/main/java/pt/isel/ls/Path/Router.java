package pt.isel.ls.Path;

import pt.isel.ls.Commands.RequestHandler;
import pt.isel.ls.Commands.RequestResult;
import pt.isel.ls.InvalidRequest;
import pt.isel.ls.Node;
import pt.isel.ls.Request.Request;
import pt.isel.ls.RouteAlreadyExistsException;
import pt.isel.ls.RouteNotFoundException;

import java.util.Optional;

public class Router {
    Node node = new Node();

    public void addRoute(String method, String path, RequestHandler requestHandler) throws RouteAlreadyExistsException {
        Node nodeFound = null;

        for (Node h : node.nodes) {
            if (h.getId().equalsIgnoreCase(method)) {
                nodeFound = h;
                break;
            }
        }

        if (nodeFound == null) {
            nodeFound = new Node();
            nodeFound.setId(method.toUpperCase());
            node.nodes.add(nodeFound);
        }

        String[] allPaths = path.split("/");

        boolean flag = false;

        for (int i = 1; i < allPaths.length; i++) {

            for (Node current : nodeFound.nodes) {
                if (current.getId().equalsIgnoreCase(allPaths[i])) {
                    nodeFound = current;
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                Node tmp = new Node();

                // Deal with variable paths
                if (allPaths[i].startsWith("{") && allPaths[i].endsWith("}")) {
                    tmp.setVariable(true);
                    tmp.setId(allPaths[i].substring(1, allPaths[i].length() - 1).toLowerCase());
                } else {
                    tmp.setId(allPaths[i].toLowerCase());
                }

                nodeFound.nodes.add(tmp);
                nodeFound = tmp;
            } else {
                flag = false;
            }
        }
        if (nodeFound.getHandler() != null)
            throw new RouteAlreadyExistsException();

        nodeFound.setHandler(requestHandler);
    }

    public Optional<RequestResult> findRoute(Request request) throws RouteNotFoundException, InvalidRequest {
        Node nodeFound = null;

        for (Node h : node.nodes) {
            if (h.getId().equalsIgnoreCase(request.getMethod().name())) {
                nodeFound = h;
                break;
            }
        }

        if (nodeFound == null) {
            throw new RouteNotFoundException();
        }

        String[] allPaths = request.getPath().split("/");

        boolean flag = false;

        //percorre todos as substrings do path users/supervisor/<id>
        //allPaths = {"users", "supervisor", "<id>"}
        for (int i = 1; i < allPaths.length; i++) {
            for (Node current : nodeFound.nodes) {
                if (current.isVariable() || current.getId().equalsIgnoreCase(allPaths[i])) {
                    if (current.isVariable()) request.addParameter(current.getId(), allPaths[i]);
                    nodeFound = current;
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                throw new RouteNotFoundException();
            } else {
                flag = false;
            }
        }

        RequestResult res = nodeFound.getHandler().execute(request);

        return res == null ? Optional.empty() : Optional.of(res);
    }
}
