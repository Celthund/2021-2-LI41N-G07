package pt.isel.ls;

import pt.isel.ls.commands.RequestHandler;
import java.util.LinkedList;

public class Node {
    // List that makes the SubLevel branch of the current node in the tree
    public LinkedList<Node> nodes = new LinkedList<>();
    // Method or Path
    private String id;
    private RequestHandler handler;
    // Flag that checks if the id of the present node is a value or a constant name(a table name)
    private boolean variable = false;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setHandler(RequestHandler handler) {
        this.handler = handler;
    }

    public RequestHandler getHandler() {
        return handler;
    }

    public void setVariable(boolean variable) {
        this.variable = variable;
    }

    public boolean isVariable() {
        return variable;
    }
}

