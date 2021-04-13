package pt.isel.ls.Commands;

import pt.isel.ls.Request;

public interface RequestHandler {
    RequestResult execute(Request request);
}
