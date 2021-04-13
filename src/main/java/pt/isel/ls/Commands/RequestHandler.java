package pt.isel.ls.Commands;

import pt.isel.ls.InvalidRequest;
import pt.isel.ls.Request.Request;

public interface RequestHandler {
    RequestResult execute(Request request) throws InvalidRequest;
}
