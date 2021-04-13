package pt.isel.ls;

import pt.isel.ls.Commands.RequestHandler;

import java.util.LinkedList;

public class Node {
    public LinkedList<Node> nodes = new LinkedList<>();
    private String id;
    private RequestHandler handler;
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
    public boolean isVariable(){
        return variable;
    }
}

