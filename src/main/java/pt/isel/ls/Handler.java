package pt.isel.ls;

import pt.isel.ls.Commands.RequestHandler;

import java.util.LinkedList;

public class Handler {
    public LinkedList<Handler> handlers = new LinkedList<>();
    private String id;
    private RequestHandler callFunction;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if(id == null || handlers == null) return false;

        return id.equals(((Handler)obj).getId());
    }

    public void setCallFunction(RequestHandler handler) {
        callFunction = handler;
    }

    public RequestHandler getCallFunction() {
        return callFunction;
    }
}
