package pt.isel.ls.routers;

import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.exceptions.*;
import pt.isel.ls.Node;
import pt.isel.ls.request.Request;
import java.util.LinkedList;

public class HandlerRouter {
    // The head of the tree
    Node node = new Node();
    LinkedList<String> routes = new LinkedList<>();

    public String print() {
        StringBuilder str = new StringBuilder();
        for (String out : routes) {
            str.append(out).append('\n');
        }
        return str.toString();
    }

    public void addRoute(String method, String path, RequestHandler requestHandler) throws RouteAlreadyExistsException {
        // Pointer that will run through the tree
        Node nodeFound = null;

        // Cycle to get the methods (GET, POST), if found it will point to it
        for (Node h : node.nodes) {
            if (h.getId().equalsIgnoreCase(method)) {
                nodeFound = h;
                break;
            }
        }


        // If not fount just creat a new node and add to the head list
        if (nodeFound == null) {
            nodeFound = new Node();
            nodeFound.setId(method.toUpperCase());
            node.nodes.add(nodeFound);
        }

        // Separates all the paths
        String[] allPaths = path.split("/");

        // Flag to check if there is already a Path in the tree so it can act accordingly
        boolean flag = false;
        String currPath;
        // Cycle that run through all the path sent
        for (int i = 1; i < allPaths.length; i++) {
            currPath = allPaths[i];

            // If exists it changes the pointer to that node and put the flag to true to know a node has been found
            if (currPath.startsWith("{") && currPath.endsWith("}")) {
                currPath = currPath.substring(1, currPath.length() - 1);
            }

            // Checks the current tree level if the path already exists
            for (Node current : nodeFound.nodes) {
                if (current.getId().equalsIgnoreCase(currPath)) {
                    nodeFound = current;
                    flag = true;
                    break;
                }
            }

            // If the flag is false it means it didnt found the present path so it will add to
            // the current node list creating
            //a new branch in the tree
            if (!flag) {
                Node tmp = new Node();

                // Checks if its a variable Path, if it is it will change the Variable flag to true
                // and then add to the id the value
                if (allPaths[i].startsWith("{") && allPaths[i].endsWith("}")) {
                    tmp.setVariable(true);
                    tmp.setId(allPaths[i].substring(1, allPaths[i].length() - 1).toLowerCase());
                } else {
                    // If it isn't a variable then just add "normally" leaving the Variable flag at false
                    tmp.setId(allPaths[i].toLowerCase());
                }

                nodeFound.nodes.add(tmp);
                nodeFound = tmp;
            } else {
                // Turns the flag back to false to continue the iteration
                flag = false;
            }
        }

        // If a Route already exists, just throws a exception
        if (nodeFound.getHandler() != null) {
            throw new RouteAlreadyExistsException("Route Already exists.");
        }

        routes.add(method + " " + path);

        // When it reaches the root, it will set the handler to the one sent by in the function parameter
        nodeFound.setHandler(requestHandler);
    }

    public RequestHandler findRoute(Request request) throws AppException {
        // Pointer that will run through the tree
        Node nodeFound = null;

        // Search for the method
        for (Node h : node.nodes) {
            if (h.getId().equalsIgnoreCase(request.getMethod().name())) {
                nodeFound = h;
                break;
            }
        }

        // If no method found throws and exception
        if (nodeFound == null) {
            throw new RouteNotFoundException();
        }

        // Separates the path string sent into substring to search to the tree for the correspondent path
        String[] allPaths = request.getPath();

        // Flag to check if a path was found in the current tree level
        boolean flag = false;

        // Runs through all the paths substring
        for (String allPath : allPaths) {
            // Cycle to check in the current ith tree level
            for (Node current : nodeFound.nodes) {
                if (current.isVariable() || current.getId().equalsIgnoreCase(allPath)) {
                    // If the variable flag in the Node class is set to true it means that the current String is a value
                    //so we add it to the parameters hashmap in Request
                    if (current.isVariable()) {
                        request.addParameter(current.getId(), allPath);
                    }
                    // Points to the next level the path was found
                    nodeFound = current;
                    // Warns that a path was found
                    flag = true;
                    break;
                }
            }
            // If flag is false it means no path in the tree was valid so, the path sent was an invalid one
            if (!flag) {
                throw new RouteNotFoundException("Route not found.");
            } else {
                // Set it back to false to continues the iteration
                flag = false;
            }
        }

        // Stores the execute to then send it
        return nodeFound.getHandler();
    }
}
