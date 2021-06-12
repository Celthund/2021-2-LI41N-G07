package pt.isel.ls.routers;

import java.util.LinkedList;
import pt.isel.ls.request.RequestHandler;

public class Node {
    // List that makes the SubLevel branch of the current node in the tree
    public LinkedList<Node> nodes = new LinkedList<>();
    // Method or Path
    private String id;
    private RequestHandler handler;
    // Flag that checks if the id of the present node is alink value or alink constant name(alink table name)
    private boolean variable = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RequestHandler getHandler() {
        return handler;
    }

    public void setHandler(RequestHandler handler) {
        this.handler = handler;
    }

    public boolean isVariable() {
        return variable;
    }

    public void setVariable(boolean variable) {
        this.variable = variable;
    }
}

