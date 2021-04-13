package pt.isel.ls.Path;
import pt.isel.ls.Commands.RequestHandler;
import pt.isel.ls.Commands.RequestResult;
import pt.isel.ls.RouteAlreadyExistsException;
import pt.isel.ls.Node;
import pt.isel.ls.Request;
import pt.isel.ls.RouteNotFoundException;

import java.util.Optional;

public class Router {
    Node node = new Node();

    public void addRoute(String method, String path,  RequestHandler requestHandler) throws RouteAlreadyExistsException {
        Node nodeFound = null;

        for (Node h: node.nodes){
            if(h.getId().equalsIgnoreCase(method)) {
                nodeFound = h;
                break;
            }
        }

        if(nodeFound == null){
            nodeFound = new Node();
            nodeFound.setId(method.toUpperCase());
            node.nodes.add(nodeFound);
        }

        String[] allPaths = path.split("/");

        boolean flag = false;

        for(int i = 1; i < allPaths.length; i++){

            for (Node current: nodeFound.nodes) {
                if(current.getId().equalsIgnoreCase(allPaths[i])){
                    nodeFound = current;
                    flag = true;
                    break;
                }
            }
            if(!flag){
                Node tmp = new Node();
                tmp.setId(allPaths[i].toLowerCase());

                // Deal with variable paths
                if(allPaths[i].startsWith("<") && allPaths[i].endsWith(">")) tmp.setVariable(true);

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

    public Optional<RequestResult> findRoute(Request request) throws RouteNotFoundException {
        Node nodeFound = null;

        for (Node h : node.nodes){
            if(h.getId().equalsIgnoreCase(request.getMethod())) {
                nodeFound = h;
                break;
            }
        }

        if(nodeFound == null){
            throw new RouteNotFoundException();
        }

        String[] allPaths = request.getPath().split("/");

        boolean flag = false;

        //percorre todos as substrings do path users/supervisor/<id>
        //allPaths = {"users", "supervisor", "<id>"}
        for(int i = 1; i < allPaths.length; i++){
            for (Node current: nodeFound.nodes) {
                if(current.isVariable() || current.getId().equalsIgnoreCase(allPaths[i])){
                    nodeFound = current;
                    flag = true;
                    break;
                }
            }
            if(!flag){
                throw new RouteNotFoundException();
            } else {
                flag = false;
            }
        }

        return Optional.of(nodeFound.getHandler().execute(request));
    }
}
